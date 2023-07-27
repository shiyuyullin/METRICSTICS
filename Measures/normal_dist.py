import numpy as np
import matplotlib.pyplot as plt

def generate_qq_plots(data_list1, data_list2):

    sorted_data1 = sorted(data_list1)
    sorted_data2 = sorted(data_list2)

    n1 = len(data_list1)
    n2 = len(data_list2)
    expected_values1 = [(i - 0.5) / n1 for i in range(1, n1 + 1)]
    expected_values2 = [(i - 0.5) / n2 for i in range(1, n2 + 1)]

    plt.figure(figsize=(14, 7))

    plt.subplot(1, 2, 1)
    plt.scatter(expected_values1, sorted_data1)
    plt.xlabel('Expected Values (Standard Normal)')
    plt.ylabel('Sorted Data')
    plt.title('Q-Q Plot - WMC')
    plt.plot([min(expected_values1), max(expected_values1)], [min(sorted_data1), max(sorted_data1)], color='red', linestyle='--')

    plt.subplot(1, 2, 2)
    plt.scatter(expected_values2, sorted_data2)
    plt.xlabel('Expected Values (Standard Normal)')
    plt.ylabel('Sorted Data')
    plt.title('Q-Q Plot - LSLOC')
    plt.plot([min(expected_values2), max(expected_values2)], [min(sorted_data2), max(sorted_data2)], color='red', linestyle='--')

    plt.tight_layout()
    plt.show()

wmc = [11, 1, 1, 1, 2, 1, 1, 5, 6, 3, 9, 1, 1, 20, 2]
lsloc = [18, 1, 4, 6, 7, 6, 3, 19, 6, 8, 13, 1, 1, 49, 6]

generate_qq_plots(wmc, lsloc)
