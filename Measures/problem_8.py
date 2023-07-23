import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import shapiro, kstest, rankdata


def scatter_plot(x_values, y_values, labels, x_label, y_label, title):
    
    plt.figure(figsize=(12, 8))

    colors = ["Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink", "Brown", "Cyan", "Magenta", "Lime", "Teal", "Indigo", "Gray", "Olive"]

    # Data points and labels appearance
    for i, label in enumerate(labels):
        plt.scatter(x_values[i], y_values[i], s=100, alpha=0.9, label=label, color=colors[i % len(colors)], edgecolors='black', linewidths=1)

    # Plot elements
    plt.xlabel(x_label, fontsize=14)
    plt.ylabel(y_label, fontsize=14)
    plt.title(title, fontsize=16, fontweight='bold')
    plt.xticks(fontsize=12)
    plt.yticks(fontsize=12)
    plt.grid(which='both', linestyle='--', alpha=0.5)
    plt.minorticks_on()

    # Plot background and borders
    # plt.gca().set_facecolor('lightgray')
    plt.gca().spines['top'].set_visible(False)
    plt.gca().spines['right'].set_visible(False)
    plt.gca().spines['bottom'].set_linewidth(0.5)
    plt.gca().spines['left'].set_linewidth(0.5)

    plt.legend(fontsize=12, loc='upper left')

    plt.tight_layout()
    plt.show()
    
def check_normality(x_values, y_values, alpha=0.05):
    # Perform Shapiro-Wilk test for normality
    _, p_value_x = shapiro(x_values)
    _, p_value_y = shapiro(y_values)

    # Perform Kolmogorov-Smirnov test for normality
    _, ks_p_value_x = kstest(x_values, 'norm')
    _, ks_p_value_y = kstest(y_values, 'norm')

    # Check if p-values are greater than alpha (null hypothesis: data is normally distributed)
    is_x_normal = p_value_x > alpha and ks_p_value_x > alpha
    is_y_normal = p_value_y > alpha and ks_p_value_y > alpha

    return is_x_normal, is_y_normal

def rank_correlation_coefficient(list1, list2):
    def get_rank(lst):
        sorted_lst = sorted(lst)
        ranks = [sorted_lst.index(item) + 1 + (sorted_lst.count(item) - 1) / 2 for item in lst]
        return ranks

    ranks1 = get_rank(list1)
    ranks2 = get_rank(list2)

    differences = [r1 - r2 for r1, r2 in zip(ranks1, ranks2)]

    sum_differences_squared = sum([diff ** 2 for diff in differences])

    n = len(list1)

    rho = 1 - (6 * sum_differences_squared) / (n * ((n ** 2) - 1))

    return rho

wmc = [11, 1, 1, 1, 2, 1, 1, 5, 6, 3, 9, 1, 1, 20, 2]
lsloc = [18, 1, 4, 6, 7, 6, 3, 19, 6, 8, 13, 1, 1, 49, 6]
label = ["CustomMath", "ComputeEmpty", "ComputeMaximum", "ComputeMean", "ComputeMeanAbsoluteDeviation", "ComputeMedian", "ComputeMinimum", "ComputeMode", "ComputeObserver", "ComputeStandardDeviation", "Event", "StringCheckingUtils", "App", "Controller", "MetricsticsUI"]

scatter_plot(lsloc, wmc, label, "LSLOC", "WMC", "Scatter Plot")

is_lsloc_normal, is_wmc_normal = check_normality(lsloc, wmc)
print(f"Is X normally distributed? {is_lsloc_normal}")
print(f"Is Y normally distributed? {is_wmc_normal}")

spearman_correlation_coefficient = rank_correlation_coefficient(wmc, lsloc)
print(f"Spearman correlation coefficient: {spearman_correlation_coefficient}")
