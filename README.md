# Websecurity with OpenAM tutorial

## Prerequisites

homebrew
ansible
openam (see ixcode/openam-server and https://github.com/ForgeRock/frstack)

## Setup Machine

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

# TODO

- Build out authentication api
- Changing UX based on role
- Backend logic depending on role
- Prevent users from looking at their own data
- Cookies?
- Multiple services
- API Gateway
