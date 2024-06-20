#!/bin/bash
#helm repo add morremeyer https://morremeyer.github.io/charts/
#helm repo update

kubens apps
helm install pdfesign morremeyer/generic --version 8.0.0 --namespace apps --values values.yaml
