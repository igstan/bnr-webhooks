#!/usr/bin/env python
import re
import requests
import flask


BNR_URL = 'http://bnr.ro/nbrfxrates.xml'


def get_bnr():
    resp = requests.get(BNR_URL)
    return resp.text


def get_date(xml):
    pattern = re.compile(r'<PublishingDate>(?P<date>[^<]+)</PublishingDate>')
    return pattern.search(xml).group('date')


def create_app():
    import clients
    app = flask.Flask(__name__)
    app.register_blueprint(clients.client_views)
    return app


if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
