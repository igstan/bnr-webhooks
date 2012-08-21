import flask


client_views = flask.Blueprint('client', __name__)


@client_views.route('/')
def homepage():
    return flask.redirect(flask.url_for('.hooks'))


@client_views.route('/hooks', methods=['GET', 'POST'])
def hooks():
    if flask.request.method == 'POST':
        return ('subscribed', 201)
    return flask.render_template('hooks.html')
