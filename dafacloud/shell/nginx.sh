#!/usr/bin/env bash
rm -rf /usr/share/nginx/www/*
unzip -d /usr/duke/ /usr/duke/dist.zip
cp -r /usr/duke/dist/* /usr/share/nginx/www/
rm -rf /usr/duke/dist*
service nginx restart