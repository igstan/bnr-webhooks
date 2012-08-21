#!/usr/bin/env python
import requests
import flask


BNR_URL = 'http://bnr.ro/nbrfxrates.xml'


def get_bnr():
    resp = requests.get(BNR_URL)
    return resp.text


def create_app():
    app = flask.Flask(__name__)
    return app


if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
