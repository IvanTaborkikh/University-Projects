package org.padadak.backEnd.algorithm;

import org.padadak.backEnd.data.Pierscien;
import org.padadak.backEnd.data.Plyta;

import java.util.ArrayList;
import java.util.List;

public class Calculating {

    public static void buildOpt(List<Pierscien> piersciens, List<Pierscien> currOpt, List<List<Pierscien>> allPosOpt, double prom)
    {
        boolean added = false;

        for (Pierscien c : piersciens)
        {
            if (!currOpt.contains(c))
            {
                if (currOpt.isEmpty())
                {
                    if (c.getPromienWew() < prom && prom < c.getPromienZew())
                    {
                        currOpt.add(c);
                        buildOpt(piersciens, currOpt, allPosOpt, prom);
                        currOpt.remove(currOpt.size() - 1);
                        added = true;
                    }
                } else
                {
                    Pierscien prev = currOpt.get(currOpt.size() - 1);
                    if (canNest(prev, c))
                    {
                        currOpt.add(c);
                        buildOpt(piersciens, currOpt, allPosOpt, prom);
                        currOpt.remove(currOpt.size() - 1);
                        added = true;
                    }
                }
            }
        }

        if (!added && !currOpt.isEmpty())
        {
            allPosOpt.add(new ArrayList<>(currOpt));
        }
    }


    public static boolean canNest(Pierscien prev, Pierscien next) {
        return prev.getPromienWew() < next.getPromienZew() && prev.getPromienWew() > next.getPromienWew();
    }

    public static void Found(List<Plyta> plytas, List<Pierscien> piersciens)
    {
        for(Plyta pl : plytas)
        {
            System.out.println("Plyta: " + pl.getNr());

            List<List<Pierscien>> allPosOpt = new ArrayList<>();
            buildOpt(piersciens,  new ArrayList<>(), allPosOpt, pl.getPromien());

            List<Pierscien> bestMinSMinC = foundBest(allPosOpt, "minSMinC");
            List<Pierscien> bestMinSMaxC = foundBest(allPosOpt, "minSMaxC");
            List<Pierscien> bestMaxSMinC = foundBest(allPosOpt, "maxSMinC");
            List<Pierscien> bestMaxSMaxC = foundBest(allPosOpt, "maxSMaxC");

            printOpt("(min S, min C)", bestMinSMinC);
            printOpt("(min S, max C)", bestMinSMaxC);
            printOpt("(max S, min C)", bestMaxSMinC);
            printOpt("(max S, max C)", bestMaxSMaxC);
            System.out.println("\n");
        }
    }

    public static List<Pierscien> foundBest(List<List<Pierscien>> allPosOpt, String mode) {
        if (allPosOpt.isEmpty())
        {
            return null;
        }

        List<Pierscien> best = null;
        double bestHeight = (mode.contains("minS")) ? Double.MAX_VALUE : -1;
        int bestCount = (mode.contains("minC")) ? Integer.MAX_VALUE : -1;

        for (List<Pierscien> opt : allPosOpt) {
            double totalHeight = opt.stream().mapToDouble(r -> r.getWysokosc()).sum();
            int count = opt.size();

            boolean better = false;
            switch (mode) {
                case "minSMinC":
                    better = totalHeight < bestHeight || (totalHeight == bestHeight && count < bestCount);
                    break;
                case "minSMaxC":
                    better = totalHeight < bestHeight || (totalHeight == bestHeight && count > bestCount);
                    break;
                case "maxSMinC":
                    better = totalHeight > bestHeight || (totalHeight == bestHeight && count < bestCount);
                    break;
                case "maxSMaxC":
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

    public static void printOpt(String label, List<Pierscien> opt) {
        double totalHeight = opt.stream().mapToDouble(r -> r.getWysokosc()).sum();
        if (opt == null)
        {
            System.out.println("Error");
            return;
        }
        System.out.println(label + " S = " + totalHeight + ", C = " + opt.size());
        System.out.print("Pierścienie: ");
        for (Pierscien p : opt)
        {
            System.out.print(p.getNr() + " ");
        }
        System.out.println("");
    }

}
