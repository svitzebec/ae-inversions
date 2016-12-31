import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Svit Zebec
 */
public class InversionsTest {

    public enum AlgorithmType {
        BF, IS1, IS2, MS1, MS2, MS3, HYBRID;

        public String toString() {
            switch (this) {
                case BF:
                    return "bf";
                case IS1:
                    return "is1";
                case IS2:
                    return "is2";
                case MS1:
                    return "ms1";
                case MS2:
                    return "ms2";
                case MS3:
                    return "ms3";
                case HYBRID:
                    return "hybrid";
                default:
                    return "";
            }
        }

        private static AlgorithmType fromString(String string) {
            switch (string) {
                case "bf":
                    return BF;
                case "is1":
                    return IS1;
                case "is2":
                    return IS2;
                case "ms1":
                    return MS1;
                case "ms2":
                    return MS2;
                case "ms3":
                    return MS3;
                case "hybrid":
                    return HYBRID;
                default:
                    return null;
            }
        }
    }

    public enum SequenceType {
        INC, DEC, RND;

        private static SequenceType fromString(String string) {
            switch (string) {
                case "inc":
                    return INC;
                case "dec":
                    return DEC;
                case "rnd":
                    return RND;
                default:
                    return null;
            }
        }
    }

    public static AlgorithmType alg = AlgorithmType.BF;
    public static int n = 1;
    public static SequenceType seq = SequenceType.RND;
    public static int reps = 1;

    public static void main(String[] args) {

        if (!parseArgs(args)) {
            System.out.println("An error occured while parsing arguments");
            return;
        }

        test(alg, n, seq, reps);
    }

    private static boolean parseArgs(String[] args) {
        if (args.length != 4) {
            return false;
        }

        AlgorithmType algorithmType = AlgorithmType.fromString(args[0]);
        if (algorithmType != null) {
            alg = algorithmType;
        } else {
            System.out.println("Could not parse algorithm type.");
            return false;
        }

        try {
            n = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Could not parse n. Error: " + e.getLocalizedMessage());
            return false;
        }

        SequenceType sequenceType = SequenceType.fromString(args[2]);
        if (sequenceType != null) {
            seq = sequenceType;
        } else {
            System.out.println("Could not parse sequence type.");
            return false;
        }

        try {
            reps = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.out.println("Could not parse reps. Error: " + e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    private static void test(AlgorithmType algorithmType, int n, SequenceType sequenceType, int reps) {

        long startTime;
        long executionTime = 0;
        long result = 0;
        int[] sequence = generateSequence(n, sequenceType);
        int [] referenceSequence = sequence.clone();

        int[] warmUpSequence = generateSequence(100, SequenceType.RND);
        int warmUpLength = 1200;

        switch (algorithmType) {
            case BF:
                for (int i = 1; i <= warmUpLength; i++) {
                    Inversions.bf(warmUpSequence);
                }

                for (int i = 1; i <= reps; i++) {
                    sequence = generateSequence(n, sequenceType);
                    startTime = System.nanoTime();
                    result = Inversions.bf(sequence);
                    executionTime += System.nanoTime() - startTime;
                }
                break;
            case IS1:
                for (int i = 1; i <= warmUpLength; i++) {
                    Inversions.is1(warmUpSequence);
                }

                for (int i = 1; i <= reps; i++) {
                    sequence = generateSequence(n, sequenceType);
                    startTime = System.nanoTime();
                    result = Inversions.is1(sequence);
                    executionTime += System.nanoTime() - startTime;
                }
                break;
            case IS2:
                for (int i = 1; i <= warmUpLength; i++) {
                    Inversions.is2(warmUpSequence);
                }

                for (int i = 1; i <= reps; i++) {
                    sequence = generateSequence(n, sequenceType);
                    startTime = System.nanoTime();
                    result = Inversions.is2(sequence);
                    executionTime += System.nanoTime() - startTime;
                }
                break;
            case MS1:
                for (int i = 1; i <= warmUpLength; i++) {
                    Inversions.ms1(warmUpSequence);
                }

                for (int i = 1; i <= reps; i++) {
                    sequence = generateSequence(n, sequenceType);
                    startTime = System.nanoTime();
                    result = Inversions.ms1(sequence);
                    executionTime += System.nanoTime() - startTime;
                }
                break;
            case MS2:
                for (int i = 1; i <= warmUpLength; i++) {
                    Inversions.ms2(warmUpSequence);
                }

                for (int i = 1; i <= reps; i++) {
                    sequence = generateSequence(n, sequenceType);
                    startTime = System.nanoTime();
                    result = Inversions.ms2(sequence);
                    executionTime += System.nanoTime() - startTime;
                }
                break;
            case MS3:
                for (int i = 1; i <= warmUpLength; i++) {
                    Inversions.ms3(warmUpSequence);
                }

                for (int i = 1; i <= reps; i++) {
                    generateSequence(n, sequenceType);
                    startTime = System.nanoTime();
                    result = Inversions.ms3(sequence);
                    executionTime += System.nanoTime() - startTime;
                }
                break;
            case HYBRID:
                for (int i = 1; i <= warmUpLength; i++) {
                    Inversions.hybrid(warmUpSequence);
                }

                for (int i = 1; i <= reps; i++) {
                    generateSequence(n, sequenceType);
                    startTime = System.nanoTime();
                    result = Inversions.hybrid(sequence);
                    executionTime += System.nanoTime() - startTime;
                }
                break;
        }

        long timeMiliseconds = executionTime / 1000000;
        System.out.printf("%s,%d,%s,%d,%d,%d\n", algorithmType.toString(), n, sequenceType.toString(), reps, result, timeMiliseconds);
    }

    private static int[] generateSequence(int size, SequenceType sequenceType) {
        int[] sequence = new int[size];
        Integer[] objectSequence = new Integer[size];

        for (int i = 0; i < size; i++) {
            objectSequence[i] = i;
        }

        List sequenceList = Arrays.asList(objectSequence);
        switch (sequenceType) {
            case INC:
                break;
            case DEC:
                Collections.reverse(sequenceList);
                sequence = toPrimitiveIntArray(sequenceList.toArray());
                break;
            case RND:
                Collections.shuffle(sequenceList);
                sequence = toPrimitiveIntArray(sequenceList.toArray());
                break;
        }

        return sequence;
    }

    private static int[] toPrimitiveIntArray(Object[] array) {
        int[] primitiveArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            primitiveArray[i] = ((Integer) (array[i])).intValue();
        }
        return primitiveArray;
    }
}

class Inversions {

    public static long bf(int[] array) {
        long count = 0;
        int length = array.length;
        for (int i = 0; i < length; i++) {
            for (int j = i+1; j < length; j++) {
                if (array[i] > array[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public static long is1(int[] array) {
        long count = 0;
        int length = array.length;
        for (int i = 1; i < length; i++) {
            int j;
            for (j = i; j > 0 && array[j-1] > array[j]; --j) {
                swap(array, j-1, j);
            }
            count += i-j;
        }
        return count;
    }

    public static long is2(int[] array) {
        long count = 0;
        int length = array.length;
        for (int i = 1; i < length; i++) {
            int x = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > x) {
                array[j + 1] = array[j];
                j--;
            }
            array[j+1] = x;
            count += i-(j+1);
        }
        return count;
    }

    private static final void swap(int[] array, int first, int second) {
        int temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    public static long ms1(int[] array) {
        return ms1Helper(array, 0, array.length);
    }

    private static long ms1Helper(int[] array, int first, int last) {
        int n = last - first;
        if (n < 2)
            return 0;

        int middle = first + n / 2;
        long count1 = ms1Helper(array, first, middle);
        long count2 = ms1Helper(array, middle, last);

        int[] b = new int[n];
        int i = first, j = middle;

        long count = 0;
        for (int k = 0; k < n; k++) {
            if (i < middle && (j >= last || array[i] <= array[j])) {
                b[k] = array[i];
                i++;
            } else {
                b[k] = array[j];
                j++;
                count += middle - i;
            }
        }
        move(b, array, 0, b.length, first);

        return count + count1 + count2;
    }

    public static long ms2(int[] array) {
        int n = array.length;
        int[] b = new int[n];
        return ms2Helper(array, 0, n, b);
    }

    private static long ms2Helper(int[] array, int first, int last, int[] b) {
        if (last - first < 2)
            return 0;

        int middle = (first + last) / 2;
        long count1 = ms2Helper(array, first, middle, b);
        long count2 = ms2Helper(array, middle, last, b);
        int i = first, j = middle;

        long count = 0;
        for (int k = first; k < last; k++) {
            if (i < middle && (j >= last || array[i] <= array[j])) {
                b[k] = array[i];
                i++;
            } else {
                b[k] = array[j];
                j++;
                count += middle - i;
            }
        }
        move(b, array, first, last, first);

        return count + count1 + count2;
    }

    public static long ms3(int[] array) {
        int n = array.length;
        int[] b = new int[n];
        long count = 0;
        for (int width = 1; width < n; width *= 2) {
            for (int first = 0; first < n; first += 2 * width) {
                int mid = Math.min(first + width, n);
                int last = Math.min(mid + width, n);
                int i = first, j = mid;
                for (int k = first; k < last; k++) {
                    if (i < mid && (j >= last || array[i] <= array[j])) {
                        b[k] = array[i];
                        i++;
                    } else {
                        b[k] = array[j];
                        j++;
                        count += mid - i;
                    }
                }
            }
            move(b, array, 0, b.length, 0);
        }
        return count;
    }

    private static void move(int[] a, int[] b, int begin, int end, int b_begin) {
        for (int i = begin; i < end; i++) {
            b[b_begin + (i - begin)] = a[i];
        }
    }

    public static long hybrid(int[] array) {
        int cutoff = 50;
        if (array.length >= cutoff) {
            return ms3(array);
        } else {
            return bf(array);
        }
    }
}
