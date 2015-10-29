package szalaimihaly.hu.worshipsearcher;


import java.util.Calendar;

/**
 * Created by Mihaly on 2015.10.21..
 */
public class DateListener {
    private Calendar calendar;

    public DateListener(){
        this.calendar=Calendar.getInstance();
    }



    public static boolean isGoodDay(int month, int day, int weekid) {
        switch (weekid) {
            case 1: {
                switch (month) {
                    case 1:
                        if (day > 31) {
                            return false;
                        }

                    case 2:
                        if (day > 29) {
                            return false;
                        }

                    case 3:
                        if (day > 31) {
                            return false;
                        }

                    case 4:
                        if (day >= 29 && day <= 30) {
                            return true;
                        }

                    case 5:
                        if (day > 31) {
                            return false;
                        }

                    case 6:
                        if (day >= 29 && day <= 30) {
                            return true;
                        }

                    case 7:
                        if (day > 30) {
                            return false;
                        }

                    case 8:
                        if (day > 31) {
                            return false;
                        }

                    case 9:
                        if (day > 30) {
                            return false;
                        }

                    case 10:
                        if (day > 31) {
                            return false;
                        }

                    case 11:
                        if (day > 30) {
                            return false;
                        }

                    case 12:
                        if (day > 31) {
                            return false;
                        }

                    default:
                        return true;
                }
            }
            case 2: {
                if (day >= 1 && day <= 7) {
                    return true;
                } else {
                    return false;
                }

            }
            case 3: {
                if (day >= 8 && day <= 14) {
                    return true;
                } else {
                    return false;
                }

            }
            case 4: {
                if (day >= 15 && day <= 21) {
                    return true;
                } else {
                    return false;
                }

            }
            case 5: {
                if (day >= 22 && day <= 28) {
                    return true;
                } else {
                    return false;
                }

            }
            case 14: {
                switch (month) {
                    case 1:
                        if (day >= 29 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }

                    case 2:
                        if (day == 29) {
                            return true;
                        } else {
                            return false;
                        }

                    case 3:
                        if (day >= 29 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }

                    case 4:
                        if (day >= 29 && day <= 30) {
                            return true;
                        } else {
                            return false;
                        }

                    case 5:
                        if (day >= 29 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }

                    case 6:
                        if (day >= 29 && day <= 30) {
                            return true;
                        } else {
                            return false;
                        }

                    case 7:
                        if (day >= 29 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }

                    case 8:
                        if (day >= 29 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }

                    case 9:
                        if (day >= 29 && day <= 30) {
                            return true;
                        } else {
                            return false;
                        }

                    case 10:
                        if (day >= 29 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }

                    case 11:
                        if (day >= 29 && day <= 30) {
                            return true;
                        } else {
                            return false;
                        }

                    case 12:
                        if (day >= 29 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }

                }
            }

            case 6: {
                if ((day >= 8 && day <= 14) || (day >= 22 && day <= 28)) {
                    return true;
                } else {
                    return false;
                }
            }
            case 7: {
                if ((day >= 1 && day <= 7) || (day >= 15 && day <= 21)
                        || (day >= 29)) {
                    return true;
                } else {
                    return false;
                }
            }
            case 10: {
                if ((day >= 1 && day <= 7) || (day >= 15 && day <= 21)) {
                    return true;
                } else {
                    return false;
                }
            }
            case 15: {
                switch (month) {
                    case 1:
                        if (day >= 25 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 2:
                        if (day >= 23 && day <= 29) {
                            return true;
                        } else {
                            return false;
                        }
                    case 3:
                        if (day >= 25 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 4:
                        if (day >= 24 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 5:
                        if (day >= 25 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 6:
                        if (day >= 24 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 7:
                        if (day >= 25 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 8:
                        if (day >= 25 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 9:
                        if (day >= 24 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 10:
                        if (day >= 25 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 11:
                        if (day >= 24 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                    case 12:
                        if (day >= 25 && day <= 31) {
                            return true;
                        } else {
                            return false;
                        }
                }

            }
            default:
                return false;
        }

    }
}
