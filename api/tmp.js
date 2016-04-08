
// ************************************ RECIPES ************************************

// List all Recipes
app.get('/listRecipes', function(req, res) {
  var db = req.db;
  var collection = db.get('recipes');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Recipes
app.post('/addRecipes', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: '' } : { msg: err }
             );
  });
});

// Update Recipes
app.post('/updateRecipes', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');
  var obj = {};
  var _id = req.body.id;

  delete req.body.id
  collection.update(_id, req.body, function(err, result){
    res.send(
             (err === null) ? { msg: 'Update complete !' } : { msg: err }
             );
  });
});

// Show Recipes
app.get('/showRecipes/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Recipes
app.get('/deleteRecipes/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

app.get('/form_add_ingredients', function(req, res, next) {
  res.render('form_add_ingredients');
});


// ************************************ PRODUCTS ************************************

// List all products
app.get('/listProducts', function(req, res) {
  var db = req.db;
  var collection = db.get('products');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Products
app.post('/addProducts', function (req, res) {
  var db = req.db;
  var collection = db.get('products');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: '' } : { msg: err }
             );
  });
});

// Show products
app.get('/showProducts/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('products');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

app.post('/showProducts', function (req, res) {
  var db = req.db;
  var collection = db.get('products');
  var ingredients = req.body.ingredients;
  var JSON_ingredients = [];

  if (typeof ingredients === 'string' ) {
    ingredients = ingredients.split();
  }
  for (var i = 0; i < ingredients.length; i++) {
    var json_obj = {};

    json_obj['name'] = ingredients[i];
    JSON_ingredients.push(json_obj);
  };

  collection.find({"ingredients" : { $all: JSON_ingredients}},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});


app.get('/showProductsByName/:name', function (req, res) {
  var db = req.db;
  var collection = db.get('products');
  var name = req.params.name;
  name = name.toLowerCase();
  collection.find({"name" : name },{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Recipes
app.get('/deleteProducts/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('products');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// ********************************** ALLERGENS **************************************
// List all products
app.get('/listAllergens', function(req, res) {
  var db = req.db;
  var collection = db.get('allergens');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Products
app.post('/addAllergens', function (req, res) {
  var db = req.db;
  var collection = db.get('allergens');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: '' } : { msg: err }
             );
  });
});

// Show products
app.get('/showAllergens/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('allergens');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Recipes
app.get('/deleteAllegens/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('allergens');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});


// ********************************** WEBSITE MAIN FEATURE **************************************

app.post('/search', function (req, res, next) {
  var db = req.db;
  var collection = db.get('recipes');
  var search = req.body.search;
  var result = [];

  console.log(search);
  collection.find({'name' : new RegExp(search)}, {}, function (e, docs) {
    res.end(JSON.stringify(docs));
  });
});

app.post('/advancedSearchRecipes', function (req, res, next) {
  var db = req.db;
  var collection = db.get('recipes');
  var result = [];
  var ingredients = req.body.ingredients;
  var JSON_ingredients = [];

  if (typeof ingredients === 'string' ) {
    ingredients = ingredients.split();
  }
  if (typeof ingredients === 'undefined') {
    ingredients = [];
  }
  for (var i = 0; i < ingredients.length; i++) {
    JSON_ingredients.push(ingredients[i]);
  };

  collection.find({ $and: [{ "country": req.body.country},
                  {"cost": {$gte: parseInt(req.body.cost1), $lte: parseInt(req.body.cost2)} },
                  {'name' : new RegExp(req.body.name)},
                  {"calories": {$gte: parseInt(req.body.calories1), $lte: parseInt(req.body.calories2)}},
                  {"ingredients.name" : { $all: JSON_ingredients}}
                  ]
                }, {}, function (e, docs) {
                  res.end(JSON.stringify(docs));
                });
});

app.post('/ensureAuth', function (req, res, next) {
  var db = req.db;
  var collection = db.get('users');
  var session;

  collection.find({'username' : req.body.username, 'password' : req.body.password},
                  {},
                  function (e, docs) {
                    res.send(docs);
                  });
});

app.post('/getAlternativeProducts', function (req, res) {
  var sess;
  var db = req.db;
  var collection = db.get('products');

  if (req.body._user) {
    var user = req.body._user;
    var preferences = user[0]['preferences'];
    var random_preference = preferences[Math.floor(Math.random() * preferences.length)];

    collection.find({'types' : random_preference}, {}, function (err, docs) {
      res.send(docs);
    });
  }
});


app.post('/getSuitability', function(req, res, next){
  var db = req.db;
  var product = db.get('products');
  var obj = {};

  if (req.body._user && req.body._id) {
    var user = req.body._user;
    var allergens = user[0]['allergens'];

    product.find({'_id' : req.body._id}, {}, function (err, docs) {
      var allergens_product = [];
      var _is_allergens = [];

      for (var i = 0; i < docs[0]['allergens'].length; i++) {
        allergens_product.push(docs[0]['allergens'][i]['name']);
      };

      for (var i = 0; i < allergens.length; i++) {
        if (allergens_product.indexOf(allergens[i]) > -1) {
          console.log('in the array');
          _is_allergens.push(allergens[i]);
        }
      }
      res.send(_is_allergens);
    });
  }
});

