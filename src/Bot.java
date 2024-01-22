import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;
import  java.util.Arrays;

public class Bot {
    private List<int[]> _variants;
    private int[] _possible_variant;

    public Bot() {
        _variants = new ArrayList<>();
        InitGame();
        _possible_variant = null;
    }

    public int[] getPossibleVariant() {
        return _possible_variant;
    }

    private void InitGame() {
        for (int d = 123; d < 10000; d++) {
            int d1 = d / 1000;
            int d2 = (d - d1 * 1000) / 100;
            int d3 = (d - d1 * 1000 - d2 * 100) / 10;
            int d4 = d % 10;

            int[] variant = new int[]{d1, d2, d3, d4};
            int distElem = (int) Arrays.stream(variant).distinct().count();

            if (distElem == 4) {
                _variants.add(variant);
            }
        }
    }

    public String offerVariant() {
        _possible_variant = _variants.get(0);
        String possible_variant = "";

        for (int i = 0; i < 4; i++) {
            possible_variant += _possible_variant[i];
        }

        return possible_variant;
    }

    public void hackSecret(int correctDigits, int digitInPlace) {
        _variants = _variants.stream().filter(x -> Game.getRightCount(_possible_variant, x) == correctDigits).toList();
        _variants = _variants.stream().filter(x -> Game.getInPlaceCount(_possible_variant, x) == digitInPlace).toList();
        _variants = getSortPopular();
    }

    //второй вариант метода hackSecret, но без Stream API

//    public void hackSecret(int correctDigits, int digitInPlace) {
//        for (int i = 0; i < _variants.size(); i++) {
//            if (_variants.size()>1) {
//                int matches = 0;
//                int inPlace = 0;
//
//                for (int j = 0; j < 4; j++) {
//                    for (int k = 0; k < 4; k++) {
//                        if (_variants.get(i)[j] == _possible_variant[k])
//                            matches++;
//                    }
//                }
//
//                if (matches != correctDigits) {
//                    _variants.remove(i);
//                    i--;
//                    continue;
//                }
//
//                for (int j = 0; j < 4; j++) {
//                    if (_variants.get(i)[j] == _possible_variant[j]) {
//                        inPlace++;
//                    }
//                }
//
//                if (inPlace != digitInPlace) {
//                    _variants.remove(i);
//                    i--;
//                }
//            }
//        }
//
//        _variants = getSortPopular();
//    }

    private ArrayList getSortPopular() {//сортировка оставшегося массива возможных вариантов по полурности цифр
        int count0 = 0;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;
        int count6 = 0;
        int count7 = 0;
        int count8 = 0;
        int count9 = 0;
        ArrayList<int[]> sortedVariants = new ArrayList<>();
        ArrayList<int[]> tmpVariants = new ArrayList<>(_variants);

        for (int[] variant : tmpVariants) {
            for (int i = 0; i < 4; i++) {
                switch (variant[i]) {
                    case 0: {
                        count0++;
                        break;
                    }
                    case 1: {
                        count1++;
                        break;
                    }
                    case 2: {
                        count2++;
                        break;
                    }
                    case 3: {
                        count3++;
                        break;
                    }
                    case 4: {
                        count4++;
                        break;
                    }
                    case 5: {
                        count5++;
                        break;
                    }
                    case 6: {
                        count6++;
                        break;
                    }
                    case 7: {
                        count7++;
                        break;
                    }
                    case 8: {
                        count8++;
                        break;
                    }
                    case 9: {
                        count9++;
                        break;
                    }
                    default:
                        break;
                }
            }
        }

        int[] counts = new int[]{count0, count1, count2, count3, count4, count5, count6, count7, count8, count9};

        counts = Arrays.stream(counts).sorted().toArray();

        for (int i = counts.length - 1; i >= 0; i--) {

            if (tmpVariants.isEmpty()) break;

            if (counts[i] == count0 && count0 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 0);
                count0 = 0;
            }else
            if (counts[i] == count1 && count1 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 1);
                count1 = 0;
            }else
            if (counts[i] == count2 && count2 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 2);
                count2 = 0;
            }else
            if (counts[i] == count3 && count3 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 3);
                count3 = 0;
            }else
            if (counts[i] == count4 && count4 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 4);
                count4 = 0;
            }else
            if (counts[i] == count5 && count5 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 5);
                count5 = 0;
            }else
            if (counts[i] == count6 && count6 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 6);
                count6 = 0;
            }else
            if (counts[i] == count7 && count7 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 7);
                count7 = 0;
            }else
            if (counts[i] == count8 && count8 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 8);
                count8 = 0;
            }else
            if (counts[i] == count9 && count9 != 0) {
                findMostPopular(tmpVariants, sortedVariants, 9);
                count9 = 0;
            }
        }

        return sortedVariants;
    }

    private static void findMostPopular(ArrayList<int[]> tmpVariants, ArrayList<int[]> sortedVariants, int digit){
        ListIterator<int[]> iter = tmpVariants.listIterator();
        while(iter.hasNext()){
            int[] elem = iter.next();
            for (int j = 0; j < 4; j++) {
                if(elem[j] == digit){
                    sortedVariants.add(elem);
                    iter.remove();
                    break;
                }
            }
        }
    }
}
