### Database example
This is an example of what the database "schema" could look like:


ThingLink here:
```json
{
  "_id": "myid",
  "_rev": "...",
  "data": {
    "type": "thinkLink",
    "data": {
      "thingA": {
        "uuid": "..."
      },
      "thingB": {
        "uuid": "..."
      },
      "data": {
        "type": "container"
      }
    }
  }
}
```

View for thinglinks: (using https://docs.couchdb.org/en/stable/ddocs/views/joins.html)
```javascript
function(doc) {
    var type = doc.data.type;
    if (type === "thing_link") {
        var things = [, doc.data.data.thingB];
        // emit things
        // using the sorting token of 20 represents that this is needed when querying for a certain UUID, but doesn't represent the value of that UUID
        emit([doc.data.data.thingA.uuid, 20], { _id: doc.data.data.thingB.uuid });
        emit([doc.data.data.thingB.uuid, 20], { _id: doc.data.data.thingA.uuid });
        
        // emit thing links
        emit([doc.data.data.thingA.uuid, 1], null);
        emit([doc.data.data.thingB.uuid, 1], null);
    } else if (type === "thing") {
        emit([doc._id, 2], null);
    }
}
```
Then do a query `...?key=<my thing UUID>`
* Find the Thing
* Find all its 
