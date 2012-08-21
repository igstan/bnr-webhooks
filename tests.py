from mock import patch
from flatkit.testing import FlaskTestCase


class PollTest(FlaskTestCase):

    @patch('app.requests')
    def test_get_bnr_returns_xml_response(self, requests):
        import app
        bnr_xml = requests.get.return_value.text
        self.assertEqual(app.get_bnr(), bnr_xml)


class ClientTest(FlaskTestCase):

    def setUp(self):
        import clients
        self.app.register_blueprint(clients.client_views)

    def test_subscribe_returns_201(self):
        client = self.app.test_client()
        resp = client.post('/hooks', data={'uri': 'http://example.com'})
        self.assertEqual(resp.status_code, 201)
