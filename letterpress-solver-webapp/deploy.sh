#!/bin/sh
pushd ../letterpress-solver > /dev/null 2>%1
echo "Building letterpress-solver..."
lein clean
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
lein check
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
lein midje
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
lein install
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
popd

echo "Building and deploying letterpress-solver-webapp..."
lein clean
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
lein check
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
lein uberjar
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
lein heroku deploy-uberjar
