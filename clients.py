import requests
import flask


client_views = flask.Blueprint('client', __name__)


def get_subscribers():
    return flask.current_app.extensions['bnrhooks-subscribers']


@client_views.record
def initialize_subscribers(state):
    state.app.extensions['bnrhooks-subscribers'] = []


def notify(xml):
    for uri in get_subscribers():
        requests.put(uri, data=xml)


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
