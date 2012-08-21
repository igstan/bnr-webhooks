import requests
import flask


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
