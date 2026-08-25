// Topic: Garbage collection (automatic memory cleanup)
package w02.oop;

// Java frees memory for you: once an object can no longer be reached by ANY
// reference variable, it becomes eligible for garbage collection and the JVM
// may reclaim its heap space. Unlike C++ there is no manual `delete`, which is
// why memory leaks from forgotten frees do not happen here.
// Key point: you can make an object eligible, but you cannot force the exact
// moment it is collected - the collector runs on its own schedule.
public class GarbageCollectionDemo {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        // ===== Example: Making an object unreachable by reassigning =====
        Note first = new Note("first note");
        Note second = new Note("second note");

        // `first` is pointed at the SAME object as `second`. Nothing refers to
        // the original "first note" object any more -> it is now garbage.
        first = second;
        System.out.println("first now says: " + first.text);
        System.out.println("The original 'first note' object is unreachable and eligible for GC.");

        // ===== Example: Making an object unreachable with null =====
        // Setting the last remaining reference to null cuts the object loose.
        Note temporary = new Note("temporary note");
        temporary = null;
        System.out.println("temporary = " + temporary + " -> its object is eligible for GC too.");

        // ===== Example: Creating a lot of garbage on purpose =====
        // Every loop pass allocates a Note and then immediately abandons it,
        // because the reference is overwritten on the next pass.
        System.out.println("Used memory before: " + usedMemoryKb(runtime) + " KB");
        Note churn = null;
        for (int i = 0; i < 200_000; i++) {
            churn = new Note("throwaway #" + i);
        }
        System.out.println("Used memory after allocating: " + usedMemoryKb(runtime) + " KB");
        System.out.println("Only the last one is still reachable: " + churn.text);

        // ===== Example: Suggesting a collection =====
        // System.gc() is only a HINT. The JVM is free to ignore it, so the
        // numbers below may or may not drop - that unpredictability IS the
        // lesson about automatic memory management.
        System.gc();
        System.out.println("Used memory after System.gc() hint: " + usedMemoryKb(runtime) + " KB");

        // NOTE on finalize(): older material shows overriding Object.finalize()
        // to run cleanup just before collection. It is deprecated for removal
        // (JEP 421) because it may never run, so it is deliberately not used
        // here. Modern cleanup uses try-with-resources / AutoCloseable instead.
    }

    // Helper: how much heap the JVM is currently using, in kilobytes.
    static long usedMemoryKb(Runtime runtime) {
        return (runtime.totalMemory() - runtime.freeMemory()) / 1024;
    }
}

class Note {
    String text;

    Note(String text) {
        this.text = text;
    }
}
