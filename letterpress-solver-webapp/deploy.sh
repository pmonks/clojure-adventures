#!/bin/sh
pushd ../letterpress-solver > /dev/null 2>%1
echo "Building letterpress-solver..."
lein do clean, check, midje, install
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
popd

echo "Building and deploying letterpress-solver-webapp..."
lein do clean, check, uberjar, heroku deploy-uberjar
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
