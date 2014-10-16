Buffer Allocators
===========

JMH benchmark for typical buffer allocation methods, as a fork of https://github.com/ashkrit/blog.git

####How to use
mvn package
java -jar target/microbenchmarks.jar -wi 3 -i 3 '.*JMH.*Test.*'
