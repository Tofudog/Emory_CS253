/*
My first attempt will use a BST.
For the BST, each subtree's root is the largest value.
Each Node in the BST is a pair (value, idx), and for each
query, update the corresponding node.

I will have an array-based heap, each with the pair (value, idx). In order to perform updates in constant time, I must have an inverse data structure. Specifically, everytime I flip two values in the heap, this second ds will record where each one is... (ds2[idx] = newidx based on heap placement)

Then, simply extract the top from index 0 in heap and output second pair...

This should be O(M + Nlog(M)) time complexity and O(M) space complexity...
*/