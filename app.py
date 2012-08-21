import requests


BNR_URL = 'http://bnr.ro/nbrfxrates.xml'


def get_bnr():
    resp = requests.get(BNR_URL)
    return resp.text
