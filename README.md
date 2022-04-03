# IN4030-oblig-4

## Introduction – what this report is about
This report is about the parallelization of the sorting algorithm Radix sort. Radix sort is a fast algorithm which does
not use comparisons whenever doing it's sortings (in comparison to e.g bubble sort). It works by grouping individual
digits that share the same significant positon and value. it uses counting sort as a subroutine to sort an array of
numbers. For parallelization, we want to have to have threads working concurrently in the sorting algorithm. More
specifically, the threads has to concurrently execute the 4 steps:
- find max value of the array - Count the different digit values in the array - Sum these countings & conclude where to
insert the digits - Move elements from the array based on the conclusion of where to insert the digits.

## 2. User guide – how to run your program (short, but essential), include a very simple example.
#### Build the program
   Build the program by running the command:
   ```
   mvn clean install -Dskiptests
   ```

#### Run the program:
Run the program by running the command:
```
java -cp target/IN4030-oblig-4-1.0-SNAPSHOT.jar src.MainParallelSort <n> <seed> <useBits> <threads>
```
- n - The program will generate n amount of random numbers. 0 means it will generate for these numbers: 1000, 10 000,
  100 000, 1 mill, 10 mill og 100 mill
- seed - Seed to generating random numbers
- useBits - How many bits we when sorting the digits. Most efficiently between 8-11
- threads - how many threads to use in the parallel sorting. 0 means to use your computer's amount of cores.

#### Examples of running:
Running for 100 000 with seed of 3, using 8 bits and all available threads
```
java -cp target/IN4030-oblig-4-1.0-SNAPSHOT.jar src.MainParallelSort 100000 3 8 0
```

Running tests for the numbers 1000, 100 000, 1 mill etc.
```
java -cp target/IN4030-oblig-4-1.0-SNAPSHOT.jar src.MainParallelSort 0 3 8 0
```


### 3. Parallel Radix sort – how you did the parallelization – consider including drawings
First step was to find the highest number in the array to sort. All threads had a subset of the array to search (divided evenly) within as such:
![alt text](docs/images/findmax.png)
All threads searched for the highest number in it's subset. If a thread finished, it waits in a cyclic barrier as such:
![alt text](docs/images/findmax1.png)
After all the threads are done finding it's max number, first cyclic barrier is released and thread 1 will start gathering the max number from
all the other threads and finally conclude the max number. Meanwhile, the other threads are waiting in the next cyclic barrier. Once thread 1 is finished, it hits the second cyclic barrier
and all threads are ready to proceed.











### Usage:

The program can be run using the command:

```
java -cp target/IN4030-oblig-4-1.0-SNAPSHOT.jar src.Main <n> <seed> <useBits> <threads>
```


#### How radix sort works (Not neccesairy to read for examiner)

##### For playing cards

- For player cards, we first establish 13 slots
- Then we stack each card in their slot (e.g 2 goes to slot number 2)
- After that, we'll sort based on the suits.
- First we collect them
- Then we sort them by suit (using 4 slots)
- Then we have a sorted deck of cards. Tadaa

##### For numbers

First, we have a serial of the numbers:
170 061 512 503 693 703 154 275 765 087 987 677 509 908

- First thing we do is to mark which digit we start sorting on. In this case the last digit of each digit (e.g 0 for
  170)
- We can sort these into 10 slots (0-9)
- Then we take the numbers each at a time, and dump them into such categories as such:
  0: 170 1: 061 2: 512 3: 503 693 703 4: 154 5: 275 765 6:
  7: 087 897 677 8: 908 9: 509

Now we're done with the first "pass". Now we identify them by their second digits as such:
0: 503 703 908 509 1: 512 2:
3:
4:
5: 156 6: 061 765 7: 170 275 677 8: 087 9: 693 897

Now we're done with the second "pass". We've sorted on the 2 lower digit. Lastly we're gonna order by the last digit:
0: 061 087 1: 154 170 2:
3:
4:
5: 503 509 512 6: 677 693 7: 703 765 8: 897 9: 908

And now we see that we have a sorted list!

- When we're creating these 'slots', they should be "n" long, as potentially, all numbers could end up in one slot
- So algorithm needs to have 10 * n space which is quite high.
- What we can do is to optimize the requirement for space to 2*n as such:
  Given the numbers again:
  170 061 512 503 693 703 154 275 765 087 987 677 509 908

So we start do add the last digit to slots, just like before, but now we count them:
0: 1 (0 which means starts at index)
1: 1 (1)
2: 1 (2)
3: 3 (3)
4: 1 (6)
5: 2 (7)
6: 0 (9)
7: 3 (9)
8: 1 (12)
9: 1 (13)

Then we have a second array, where we allocate the slots in. so for e.g 0 entry is the first slot. And then the next
entry (which is 1 digits) is at slot number 1 Then the next slot is 2 which starts at slot 2 Then the next slot is 3, so
we have 3 slots for that.

- This means that as we're grabbing the numbers from the first array, we also have the indexes for where to put them. So
  the array looks like this:
  [170 (0), 061 (1), 512 (2), 503 (3), 693 (3), 703 (3), 154 (4), 275, 765, ....]
  We basically keep a counter of where to insert the numbers. So we've copied over the entries to an array of size N,
  and we have 2 arrays of size N. We're basically using an array N as a helper as we saw. Then we can basically continue
  by replacing the first array again, using it as an helper. So we swap these during each "pass"  

