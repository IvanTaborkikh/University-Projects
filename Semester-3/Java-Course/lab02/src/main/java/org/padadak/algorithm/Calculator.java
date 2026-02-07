package org.padadak.algorithm;

import org.padadak.model.Plate;
import org.padadak.model.Ring;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    public static void buildOpt(List<Ring> rings, List<Ring> currentOpt, List<List<Ring>> allPosOpt, double plateRadius)
    {
        boolean added = false;

        for (Ring c : rings)
        {
            if (!currentOpt.contains(c))
            {
                if (currentOpt.isEmpty())
                {
                    if (c.getInnerRadius() < plateRadius && plateRadius < c.getOuterRadius())
                    {
                        currentOpt.add(c);
                        buildOpt(rings, currentOpt, allPosOpt, plateRadius);
                        currentOpt.remove(currentOpt.size() - 1);
                        added = true;
                    }
                } else
                {
                    Ring prev = currentOpt.get(currentOpt.size() - 1);
                    if (canNest(prev, c))
                    {
                        currentOpt.add(c);
                        buildOpt(rings, currentOpt, allPosOpt, plateRadius);
                        currentOpt.remove(currentOpt.size() - 1);
                        added = true;
                    }
                }
            }
        }

        if (!added && !currentOpt.isEmpty())
        {
            allPosOpt.add(new ArrayList<>(currentOpt));
        }
    }


    public static boolean canNest(Ring prev, Ring next) {
        return prev.getInnerRadius() < next.getOuterRadius() && prev.getInnerRadius() > next.getInnerRadius();
    }

    public static void Found(List<Plate> plytas, List<Ring> piersciens)
    {
        for(Plate pl : plytas)
        {
            System.out.println("Plate: " + pl.getId());

            List<List<Ring>> allPosOpt = new ArrayList<>();
            buildOpt(piersciens,  new ArrayList<>(), allPosOpt, pl.getRadius());

            List<Ring> bestMinSMinC = foundBest(allPosOpt, "minHMinC");
            List<Ring> bestMinSMaxC = foundBest(allPosOpt, "minHMaxC");
            List<Ring> bestMaxSMinC = foundBest(allPosOpt, "maxHMinC");
            List<Ring> bestMaxSMaxC = foundBest(allPosOpt, "maxHMaxC");

            printOpt("(min H, min C)", bestMinSMinC);
            printOpt("(min H, max C)", bestMinSMaxC);
            printOpt("(max H, min C)", bestMaxSMinC);
            printOpt("(max H, max C)", bestMaxSMaxC);
            System.out.println("\n");
        }
    }

    public static List<Ring> foundBest(List<List<Ring>> allPosOpt, String mode) {
        if (allPosOpt.isEmpty())
        {
            return null;
        }

        List<Ring> best = null;
        double bestHeight = (mode.contains("minH")) ? Double.MAX_VALUE : -1;
        int bestCount = (mode.contains("minC")) ? Integer.MAX_VALUE : -1;

        for (List<Ring> opt : allPosOpt) {
            double totalHeight = opt.stream().mapToDouble(r -> r.getHeight()).sum();
            int count = opt.size();

            boolean better = false;
            switch (mode) {
                case "minHMinC":
                    better = totalHeight < bestHeight || (totalHeight == bestHeight && count < bestCount);
                    break;
                case "minHMaxC":
                    better = totalHeight < bestHeight || (totalHeight == bestHeight && count > bestCount);
                    break;
                case "maxHMinC":
                    better = totalHeight > bestHeight || (totalHeight == bestHeight && count < bestCount);
                    break;
                case "maxHMaxC":
                    better = totalHeight > bestHeight || (totalHeight == bestHeight && count > bestCount);
                    break;
            }

            if (better)
            {
                best = opt;
                bestHeight = totalHeight;
                bestCount = count;
            }
        }

        return best;
    }

    public static void printOpt(String label, List<Ring> opt) {
        double totalHeight = opt.stream().mapToDouble(r -> r.getHeight()).sum();
        if (opt == null)
        {
            System.out.println(label + ": No solution found");
            return;
        }
        System.out.println(label + " H = " + totalHeight + ", C = " + opt.size());
        System.out.print("Rings: ");
        for (Ring p : opt)
        {
            System.out.print(p.getId() + " ");
        }
        System.out.println("");
    }

}
