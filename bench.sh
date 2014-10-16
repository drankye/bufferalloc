#Link to Blog - http://ashkrit.blogspot.sg/2013/07/which-memory-is-faster-heap-or.html

#source ~/test-jdk8.sh 

java -version

javac *.java

SIZE=10000000


#java -Xms2g -Xmx2g test.allocation.TestMemoryAllocator HEAP $SIZE

#java -Xms2g -Xmx2g test.allocation.TestMemoryAllocator BB $SIZE

java -Xms2g -Xmx2g test.allocation.TestMemoryAllocator DBB $SIZE

java -Xms2g -Xmx2g test.allocation.TestMemoryAllocator OFFHEAP $SIZE


