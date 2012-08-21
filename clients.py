import flask


client_views = flask.Blueprint('client', __name__)


@client_views.route('/')
def homepage():
    return flask.redirect(flask.url_for('.hooks'))


@client_views.route('/hooks')
def hooks():
    return flask.render_template('hooks.html')
