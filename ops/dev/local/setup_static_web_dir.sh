#!/usr/bin/env bash


# Run this script from this directory!!

sudo mkdir -p /srv/html
sudo chown -R ${USER} /srv/html

export PUBLIC_HTML="`dirname $0`/../../../resources/web_application/openam/public"

echo $PUBLIC_HTML
ls $PUBLIC_HTML
#ln -s $PUBLIC_HTML /srv/html/websecurity.tutorial.com