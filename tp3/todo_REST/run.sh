PATH=/usr/bin:$PATH

if ! test -d node_modules
then
  echo Chargement des dépendances
  export http_proxy=http://cache.univ-lille1.fr:3128
  export https_proxy=
  export HTTPS_PROXY=
  npm install
fi

node server.js
