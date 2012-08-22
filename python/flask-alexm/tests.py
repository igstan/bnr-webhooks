from mock import patch, call
from flatkit.testing import FlaskTestCase


class PollTest(FlaskTestCase):

    @patch('bnrhooks.requests')
    def test_get_bnr_returns_xml_response(self, requests):
        import bnrhooks
        bnr_xml = requests.get.return_value.text
        self.assertEqual(bnrhooks.get_bnr(), bnr_xml)

    def test_get_date_returns_the_date_string(self):
        import bnrhooks
        mock_xml = "<PublishingDate>2012-08-21</PublishingDate>"
        self.assertEqual(bnrhooks.get_date(mock_xml), '2012-08-21')


class ClientTest(FlaskTestCase):

    def setUp(self):
        import bnrhooks
        bnrhooks.setup_database(self.app)
        self.app.register_blueprint(bnrhooks.client_views)
        self.client = self.app.test_client()

    def test_subscribe_returns_201(self):
        resp = self.client.post('/hooks', data={'uri': 'http://example.com'})
        self.assertEqual(resp.status_code, 201)

    def test_subscribe_url_is_saved(self):
        import bnrhooks
        URI = 'http://example.com'
        resp = self.client.post('/hooks', data={'uri': URI})
        with self.app.app_context():
            dbs = self.app.dbs
            subscribers = [r['uri'] for r in dbs['subscriber'].find()]
            self.assertEqual(subscribers, [URI])

    @patch('bnrhooks.requests')
    def test_xml_is_put_to_subscriber(self, requests):
        import bnrhooks
        URI = 'http://example.com'
        MOCK_XML = "<PublishingDate>2012-08-21</PublishingDate>"
        with self.app.app_context():
            self.app.dbs['subscriber'].new(uri=URI)
            bnrhooks.notify(MOCK_XML, '2012-08-21')
        self.assertEqual(requests.put.mock_calls,
                         [call(URI + '/2012-08-21.xml', data=MOCK_XML)])
