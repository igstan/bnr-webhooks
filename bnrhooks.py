#!/usr/bin/env python
import re
import requests
import flask
from flask.ext.script import Manager

BNR_URL = 'http://bnr.ro/nbrfxrates.xml'

client_views = flask.Blueprint('client', __name__)


def notify(xml):
    for row in flask.current_app.dbs['subscriber'].find():
        requests.put(row['uri'], data=xml)


@client_views.route('/')
def homepage():
    return flask.redirect(flask.url_for('.hooks'))


@client_views.route('/hooks', methods=['GET', 'POST'])
def hooks():
    if flask.request.method == 'POST':
        dbs = flask.current_app.dbs
        dbs['subscriber'].new(uri=flask.request.form['uri'])
        return ('subscribed', 201)
    return flask.render_template('hooks.html')


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
    app = flask.Flask(__name__, instance_relative_config=True)
    app.config.from_pyfile('settings.py')
    setup_database(app)
    app.register_blueprint(client_views)
    return app


manager = Manager(create_app)


if __name__ == '__main__':
    manager.run()
