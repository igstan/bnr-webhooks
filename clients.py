import flask


client_views = flask.Blueprint('client', __name__)


@client_views.route('/')
def homepage():
    return flask.render_template('homepage.html')
