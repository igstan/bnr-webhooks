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


def setup_database(app):
    from werkzeug.local import LocalProxy
    from flask.ext.htables import HTables

    HTables(app)

    def _get_session():
        return flask.current_app.extensions['htables'].session

    dbs = app.dbs = LocalProxy(_get_session)

    with app.app_context():
        dbs['subscriber'].create_table()


def create_app():
    import clients
    app = flask.Flask(__name__, instance_relative_config=True)
    app.config.from_pyfile('settings.py')
    setup_database(app)
    app.register_blueprint(clients.client_views)
    return app


if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
