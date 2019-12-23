package page.danya.models;


import javax.persistence.Entity;


public enum Absent {
    present, notPresent, badNotPresent, goodNotPresent
    // present- присуствует
    // notPresent- отсуствует
    // badNotPresent- неуважительный допуск
    // goodNotPresent- уважительный допуск
}
