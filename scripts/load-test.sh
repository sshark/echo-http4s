#!/bin/bash

ab -n 1000 -c 1000 "http://localhost:7000/echo?message=hello&delay=600"
