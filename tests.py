from mock import patch
from flatkit.testing import FlaskTestCase


class PollTest(FlaskTestCase):

    @patch('app.requests')
    def test_get_bnr_returns_xml_response(self, requests):
        import app
        bnr_xml = requests.get.return_value.text
        self.assertEqual(app.get_bnr(), bnr_xml)
