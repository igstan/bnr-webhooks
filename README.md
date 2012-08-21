## BASIC SPECS

1. $BASE_URL depends on who's hosting the service

2. `GET $BASE_URL/hooks` displays an HTML form with which you can register your
   hook URL.

3. `POST $BASE_URL/hooks` accepts an `url` parameter which should be the value
   of the URL where the XML string should be PUT. Should respond with `201
   Created` on success. The value of the `url` will henceforth be named
   `$REGISTERED_URL`.

4. `PUT $REGISTERED_URL/YYYY-MM-DD.xml` is where the XML feed will be PUT. The
   XML will the request body. PUT instead of POST because we need idempotence
   in case the server who's issuing the request thinks that the request failed
   whereas it actually succeeded. Should respond with `201 Created` on success
   and 200 OK or 204 No Content if the resource was already there.
   YYYY-MM-DD.xml will be generated using the value of the
   `/DataSet/Header/PublishingDate` tag present in BNR's XML feed.

5. The push server must poll the http://bnr.ro/nbrfxrates.xml address each day
   around 13:00 hours, fetch the XML, cache it and push it to registered URLs.


## A MORE DISTRIBUTED APPROACH

Modify step 4 so that instead of PUT-ing an XML blob to the registered URL, you
instead PUT a JSON object with the following structure:

```json
{
  "rates": $BNR_XML_BLOB,
  "forward": [
    $URL_1,
    ...
    $URL_5
  ]
}
```

Alternative: use multiple `Forward-To` headers, each with a different URL to
post the XML to.

The server receiving the PUT request should forward the XML blob to all the
URLs in the forward list. ( How do we stop an infinite Internet loop if that
user decides to create its own forward URLs list? Need a way to enforce max
forwards).

So, for every 6 registered URLs, you PUT the body to some URL and add the rest
of five to the forward list. The server that receives the PUT request should
act just like our server (recursive HTTP calls).

How do we ensure fair-play, i.e. a certain server will actually forward the
request? One way would be to sometimes include a verifying URL to the list
of forward URLs and grade servers as trustworthy or not.

Is there an HTTP built-in mechanism for this kind of forwarding?

Note that each of the servers that receives PUT requests may act as registrar
themselves, thus someone may choose to register with several servers in order
to achieve eventual consistency in case some of the push servers is down. This
is where an infinite forward loop might occur.

Also, note that in this scenario push servers need not poll the BNR web sites.
It can push values to its registered URLs when another push server will notify
it about the updated rates.
