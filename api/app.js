var express = require('express')
, passport = require('passport')
, util = require('util')
, FacebookStrategy = require('passport-facebook').Strategy
, FacebookTokenStrategy = require('passport-facebook-token')
, GoogleStrategy = require('passport-google-oauth').OAuth2Strategy
, logger = require('morgan')
, session = require('express-session')
, bodyParser = require("body-parser")
, cookieParser = require("cookie-parser")
, methodOverride = require('method-override');

var mongo = require('mongodb');
var monk = require('monk');
var db = monk('mongodb://root:123456@ds049624.mongolab.com:49624/nourriture');
var request = require('request');

var FACEBOOK_APP_ID = "322980324492560";
var FACEBOOK_APP_SECRET = "745cc0ed81f3de714e42d6fd086abff5";
var GOOGLE_CLIENT_ID = "961840791432-kmmtn60o69622kgl2gsdia8d3kpdc6j4.apps.googleusercontent.com";
var GOOGLE_CLIENT_SECRET = "vQJtPVgD6E7HpDzFC7Y96k_Y";
var removeDiacritics = require('diacritics').remove;

// *************************************** PASSPORT **************************************************
passport.serializeUser(function(user, done) {
  done(null, user);
});

passport.deserializeUser(function(obj, done) {
  done(null, obj);
});

passport.use(new FacebookTokenStrategy({
  clientID: FACEBOOK_APP_ID,
  clientSecret: FACEBOOK_APP_SECRET
}, function(accessToken, refreshToken, profile, done) {
  User.findOrCreate({facebookId: profile.id}, function (error, user) {
    return done(error, user);
  });
}
));

passport.use(new GoogleStrategy({
  clientID: GOOGLE_CLIENT_ID,
  clientSecret: GOOGLE_CLIENT_SECRET,
  callbackURL: "https://nourritureapi.herokuapp.com/auth/google/callback"
},
function(accessToken, refreshToken, profile, done) {
    // asynchronous verification, for effect...
    process.nextTick(function () {
      var collection = db.get('users');

      collection.find({'profile.id': profile.id}, {}, function (e, user)
      {
        if (isEmpty(user) === false)
        {
          return done(null, user);
        }
        else {
         request.post(
         {
          url: 'http://nourritureapi.herokuapp.com/addUsers',
          method: 'POST',
          form:
          {
            _access_token: accessToken,
            profile: profile
          }
        },
        function (error, response, body)
        {
          return done(null, profile);
        });

       }
     });
    });
  }
  ));

passport.use(new FacebookStrategy({
  clientID: FACEBOOK_APP_ID,
  clientSecret: FACEBOOK_APP_SECRET,
  callbackURL: "https://nourritureapi.herokuapp.com/auth/facebook/callback"
},
function(accessToken, refreshToken, profile, done) {
  process.nextTick(function () {
    var collection = db.get('users');

    collection.find({'_facebook_id': profile.id}, {}, function (e, user)
    {
      if (user)
      {
        return done(null, user);
      }
      else
      {

       request.post(
       {
        url: 'http://nourritureapi.herokuapp.com/addUsers',
        method: 'POST',
        form:
        {
          _access_token: accessToken,
          profile: profile,
        }
      },
      function (error, response, body)
      {
        return done(null, profile);
      });

     }
   });
  });
}
));
var app = express();

app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.use(logger());
app.use(cookieParser());
app.use(bodyParser());
app.use(methodOverride());
app.use(session({ secret: 'keyboard cat' }));
app.use(passport.initialize());
app.use(passport.session());
app.use(express.static(__dirname + '/public'));

app.use(function(req,res,next){
  req.db = db;
  next();
});

app.get('/', function(req, res){
  res.render('index', { user: req.user });
});

app.get('/account', ensureAuthenticated, function(req, res){
  res.render('account', { user: req.user });
});

app.get('/login', function(req, res){
  res.render('login', { user: req.user });
});

app.post('/auth/facebook/token',
         passport.authenticate('facebook-token'),
         function (req, res) {
    // do something with req.user
    res.send(req.user? 200 : 401);
  }
  );

app.get('/auth/google',
        passport.authenticate('google', { scope: ['https://www.googleapis.com/auth/plus.login'] }),
        function(req, res){
        });

app.get('/auth/google/callback',
        passport.authenticate('google', { successRedirect: '/success',
                              failureRedirect: '/login' }),
        function(req, res) {
          res.redirect('/');
        });


app.get('/auth/facebook',
        passport.authenticate('facebook'),
        function(req, res){
        });

app.get('/auth/facebook/callback',
        passport.authenticate('facebook', { successRedirect: '/success',
                              failureRedirect: '/failure' }),
        function(req, res) {
          res.redirect('/');
        });

app.get('/success', function(req, res) {
  res.send('SUCCESS');
});

app.get('/failure', function(req, res) {
  res.send('Connection failed !');
});

app.get('/logout', function(req, res){
  req.logout();
  res.redirect('/');
});
app.listen(process.env.PORT || 5000);

function ensureAuthenticated(req, res, next) {
  if (req.isAuthenticated()) { return next(); }
  res.redirect('/login')
}

function isEmpty(obj) {
  for (var prop in obj) {
    if (obj.hasOwnProperty(prop))
      return false;
  }

  return true;
}

// ************************************ INGREDIENTS ************************************

// List all ingredients
app.get('/listIngredients', function(req, res) {
  var db = req.db;
  var collection = db.get('ingredients');

  collection.find({},{},function(e,docs){
    console.log(docs);
    res.end(JSON.stringify(docs));
  });
});

// Add ingredients
app.post('/addIngredients', function (req, res) {
  var info = {'calories' : req.body.calories};
  var db = req.db;
  var collection = db.get('ingredients');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: 'Success' } : { msg: err }
             );
  });
});

// Show ingredients
app.get('/showIngredients/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('ingredients');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete ingredients
app.get('/deleteIngredients/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('ingredients');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// ********************************** USERS **************************************
// List all Users

app.get('/listUsers', function(req, res) {
  var db = req.db;
  var collection = db.get('users');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Users

app.post('/addUsers', function (req, res) {
  var db = req.db;
  var collection = db.get('users');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: '' } : { msg: err }
             );
  });
});

// Show Users

app.get('/showUsers/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('users');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Recipes
app.get('/deleteUsers/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('users');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// ********************************** Restaurants **************************************
// List all Restaurants

app.get('/listRestaurants', function(req, res) {
  var db = req.db;
  var collection = db.get('restaurants');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Restaurants

app.post('/addRestaurants', function (req, res) {
  var db = req.db;
  var collection = db.get('restaurants');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: '' } : { msg: err }
             );
  });
});

// Show Restaurants

app.get('/showRestaurants/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('restaurants');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Restaurants
app.get('/deleteRestaurants/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('restaurants');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});


module.exports = app;
