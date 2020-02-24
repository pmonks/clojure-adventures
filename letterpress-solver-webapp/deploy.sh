#!/bin/sh
pushd ../letterpress-solver > /dev/null 2>%1
echo "Building letterpress-solver..."
lein do clean, check, midje, install
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
popd

echo "Building and deploying letterpress-solver-webapp..."
echo "Note: assumes you're already logged into Heroku and have heroku's Java plugin installed."
#heroku login                  # Only needed if not already logged in
#heroku plugins:install java   # Only needed if not already installed
#lein do clean, check, uberjar, heroku deploy-uberjar
lein do clean, check, uberjar
heroku deploy:jar target/letterpress-solver-webapp-0.1.0-SNAPSHOT-standalone.jar --app "pmonks-letterpress-solver"
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
