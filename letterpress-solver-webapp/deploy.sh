#!/bin/sh
lein clean
lein uberjar
cp target/letterpress-solver-webapp-0.1.0-SNAPSHOT-standalone.jar ../../letter-solver-heroku/letterpress-solver-webapp.jar
