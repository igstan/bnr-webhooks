#!/usr/bin/env python
import flask


app = flask.Flask(__name__)


@app.route('/receive/<string:date>.xml', methods=['PUT'])
def receive(date):
    print date
    print flask.request.data
    return 'thank you'


if __name__ == '__main__':
    app.run(debug=True, port=5001)
