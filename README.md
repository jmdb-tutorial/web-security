# Websecurity with OpenAM tutorial

## Prerequisites

homebrew
ansible
openam (see ixcode/openam-server and https://github.com/ForgeRock/frstack)

## Setup Machine

# Vagrant

Type 

vagrant up
vagrant rsync-auto

cd ops/dev/local/
./configure_local_machine.sh

## Phase 1 - Using REST apis

In this phase we will explore a very simple javascript application that directly interacts with openam

- Secure access to api calls
- Define mappings of user roles to permissions
- Control the display of elements on the page based on roles.
 
## Phase 2 - Protecting using an agent

In this phase we will extend our application so that it is protected by an agent which means we will redirect to the 
open am server for login.

- Get Role information into services

# NGINX

brew install nginx

https://gist.github.com/netpoetica/5879685
http://learnaholic.me/2012/10/10/installing-nginx-in-mac-os-x-mountain-lion/

https://unix.stackexchange.com/questions/47436/why-is-the-root-directory-on-a-web-server-put-by-default-in-var-www

html is in here /usr/local/Cellar/nginx/1.8.0/html

sudo mkdir -p /srv/html/
chown -R xxx /srv/html/

ln -s PATH_TO_SOURCE /srv/html/websecurity.tutorial.com

https://stackoverflow.com/questions/10034846/how-to-get-username-from-opensso-openam-system

# Apache

sudo su -
apachectl start

http://jason.pureconcepts.net/2014/11/install-apache-php-mysql-mac-os-x-yosemite/

https://stackoverflow.com/questions/9110179/adding-virtualhost-fails-access-forbidden-error-403-xampp-windows-7

Get it up and running and then go to access control / realms / agents

Add a new web agent

name apache-agent
password apachedemo

Store properties locally

server: http://loan.example.com:9009/openam
agent: http://websecurity.tutorial.com

# TODO

- Changing UX based on role
- Backend logic depending on role
- Prevent users from looking at their own data
- Cookies?
- Multiple services - services have their own users plus pass information in the header
- API Gateway
- Writing up
- Permissions based authz

