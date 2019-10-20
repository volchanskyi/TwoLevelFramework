FROM selenium/base:3.141.59-vanadium
LABEL authors=SeleniumHQ

USER root

#==============
# Xvfb
#==============
RUN apt-get update -qqy \
  && apt-get -qqy install \
    xvfb \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/*
