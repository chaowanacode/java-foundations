// Topic: Dynamic memory allocation (`new` + the heap)
package w02.oop;

import java.util.Scanner;

// Objects are allocated at RUNTIME, not at compile time. The `new` keyword asks
// the JVM for space on the heap while the program is running and returns a
// REFERENCE (an address) to that space. The reference variable itself lives in
// the current method's stack frame; the object lives on the heap.
public class DynamicMemoryAllocationDemo {
    public static void main(String[] args) {
        // ===== Example: `new` returns a reference =====
        // `box` sits on the stack and holds an address; the Box object it
        // points to was carved out of the heap the moment `new` ran.
        Box box = new Box(10);
        System.out.println("box.size = " + box.size);

        // ===== Example: Two references, ONE object =====
        // Copying a reference copies the address, not the object. Both variables
        // now point at the same heap object, so a change through one is visible
        // through the other - the "IOU note" idea from the notes.
        Box alias = box;
        alias.size = 99;
        System.out.println("changed through alias -> box.size = " + box.size);
        System.out.println("same object? " + (box == alias));   // == compares addresses

        // ===== Example: Two separate objects =====
        // Each `new` allocates a brand new chunk of heap memory.
        Box other = new Box(99);
        System.out.println("box.size == other.size? " + (box.size == other.size));
        System.out.println("box == other (same address)? " + (box == other));

        // ===== Example: The size is decided while the program runs =====
        // This is what "dynamic" means: the amount of memory depends on input
        // that does not exist until runtime, so it cannot be fixed at compile
        // time the way a plain `int` local variable is.
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many boxes do you want to allocate?: ");
        int count = scanner.nextInt();

        // The array object itself is allocated on the heap...
        Box[] boxes = new Box[count];
        // ...but its slots start as null: an array of references is NOT an
        // array of objects until each slot gets its own `new`.
        System.out.println("boxes[0] before filling: " + boxes[0]);

        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new Box(i);
        }
        System.out.println("Allocated " + boxes.length + " Box objects on the heap.");
        System.out.println("Last one holds size = " + boxes[boxes.length - 1].size);
        scanner.close();
    }
}

class Box {
    int size;

    Box(int size) {
        this.size = size;
    }
}
