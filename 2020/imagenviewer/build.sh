#!/bin/bash

npm run build
cp -rp build/* /var/www/html/
ln -s /var/imagen/2020/data/seed /var/www/html/

