package app.parvin.weatherappp.util



fun <T1, T2, T3, R> zip(
    list1: List<T1>,
    list2: List<T2>,
    list3: List<T3>,
    transform: (T1, T2, T3) -> R
): List<R> {
    require(list1.size == list2.size && list2.size == list3.size) {
        "All lists must have same size -> list1 = ${list1.size} , list2 = ${list2.size}" +
                ", list3 = ${list3.size}"
    }

    val it1 = list1.iterator()
    val it2 = list2.iterator()
    val it3 = list3.iterator()

    return buildList {
        while (it1.hasNext()) {
            add(transform(it1.next(), it2.next(), it3.next()))
        }
    }
}

fun <T1, T2, R> zip(
    list1: List<T1>,
    list2: List<T2>,
    transform: (T1, T2) -> R
): List<R> {
    require(list1.size == list2.size) {
        "Lists must have the same size"
    }
    val it1 = list1.iterator()
    val it2 = list2.iterator()

    return buildList {
        while (it1.hasNext()) {
            add(transform(it1.next(), it2.next()))
        }
    }
}

fun <T1, T2, T3, T4, R> zip(
    list1: List<T1>,
    list2: List<T2>,
    list3: List<T3>,
    list4: List<T4>,
    transform: (T1, T2, T3, T4) -> R
): List<R> {
    require(list1.size == list2.size && list2.size == list3.size && list3.size == list4.size) {
        "All lists must have same size -> list1 = ${list1.size} , list2 = ${list2.size}" +
                ", list3 = ${list3.size}, list3 = ${list4.size}"
    }

    val it1 = list1.iterator()
    val it2 = list2.iterator()
    val it3 = list3.iterator()
    val it4 = list4.iterator()

    return buildList {
        while (it1.hasNext()) {
            add(transform(it1.next(), it2.next(), it3.next(), it4.next()))
        }
    }
}
fun <T1, T2, T3, T4, T5, R> zip(
    list1: List<T1>,
    list2: List<T2>,
    list3: List<T3>,
    list4: List<T4>,
    list5: List<T5>,
    transform: (T1, T2, T3, T4, T5) -> R
): List<R> {
    require(list1.size == list2.size && list2.size == list3.size && list3.size == list4.size) {
        "All lists must have same size -> list1 = ${list1.size} , list2 = ${list2.size}" +
                ", list3 = ${list3.size}, list3 = ${list4.size}"
    }

    val it1 = list1.iterator()
    val it2 = list2.iterator()
    val it3 = list3.iterator()
    val it4 = list4.iterator()
    val it5 = list5.iterator()

    return buildList {
        while (it1.hasNext()) {
            add(transform(it1.next(), it2.next(), it3.next(), it4.next(), it5.next()))
        }
    }
}

