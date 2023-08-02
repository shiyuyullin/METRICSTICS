import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import numpy as np
from bokeh.io import show
from bokeh.plotting import figure
from bokeh.models import ColorBar, LinearColorMapper
from bokeh.transform import transform
from bokeh.layouts import column
from bokeh.palettes import RdBu
import seaborn as sns
from scipy.stats import shapiro, kstest, rankdata

def scatter_plot(x_values, y_values, labels, x_label, y_label):
    
    plt.figure(figsize=(14, 7))

    colors = ["Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink", "Brown", "Cyan", "Magenta", "Lime", "Teal", "Indigo", "Gray", "Olive"]

    # Data points and labels appearance
    for i, label in enumerate(labels):
        plt.scatter(x_values[i], y_values[i], s=100, alpha=0.9, label=label, color=colors[i % len(colors)], edgecolors='black', linewidths=1)

    # Plot elements
    plt.xlabel(x_label, fontsize=14)
    plt.ylabel(y_label, fontsize=14)
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
    plt.title("Scatter Plot for WMC and Logical SLOC", fontsize=16, fontweight='bold')
    plt.tight_layout()
    plt.show()
    
def check_normality(psloc, lsloc, wmc, cf, lcom, alpha=0.05):
    # Perform Shapiro-Wilk test for normality
    _, p_value_psloc = shapiro(psloc)
    _, p_value_lsloc = shapiro(lsloc)
    _, p_value_wmc = shapiro(wmc)
    _, p_value_cf = shapiro(cf)
    _, p_value_lcom = shapiro(lcom)

    # Perform Kolmogorov-Smirnov test for normality
    _, ks_p_value_psloc = kstest(psloc, 'norm')
    _, ks_p_value_lsloc = kstest(lsloc, 'norm')
    _, ks_p_value_wmc = kstest(wmc, 'norm')
    _, ks_p_value_cf = kstest(cf, 'norm')
    _, ks_p_value_lcom = kstest(lcom, 'norm')

    # Check if p-values are greater than alpha (null hypothesis: data is normally distributed)
    is_psloc_normal = p_value_psloc > alpha and ks_p_value_psloc > alpha
    is_lsloc_normal = p_value_lsloc > alpha and ks_p_value_lsloc > alpha
    is_wmc_normal = p_value_wmc > alpha and ks_p_value_wmc > alpha
    is_cf_normal = p_value_cf > alpha and ks_p_value_cf > alpha
    is_lcom_normal = p_value_lcom > alpha and ks_p_value_lcom > alpha

    return is_psloc_normal, is_lsloc_normal, is_wmc_normal, is_cf_normal, is_lcom_normal

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

def generate_heatmap(psloc, lsloc, wmc, cf, lcom):
    df = pd.DataFrame({'PSLOC': psloc, 'LSLOC': lsloc, 'WMC': wmc, 'CF*': cf, 'LCOM*': lcom})
    correlation_matrix = df.corr(method='spearman')
    mask = np.triu(np.ones_like(correlation_matrix, dtype=bool), k=1)
    plt.figure(figsize=(15, 13))
    sns.heatmap(correlation_matrix, annot=True, center=0, 
                fmt='.2f', linewidths=0.5, mask=mask, annot_kws={"size": 18})
    sns.set(font_scale=1.2)
    sns.color_palette("cubehelix", as_cmap=True)
    plt.title("Spearman's Correlation Heatmap for Metrics", fontsize=22)
    plt.tight_layout(rect=[0, 0, 0.95, 1])
    plt.xticks(fontsize=18) 
    plt.yticks(fontsize=18)
    plt.show()

def bokeh_heatmap(psloc, lsloc, wmc, cf, lcom, label):

    df = pd.DataFrame({'PSLOC': psloc, 'LSLOC': lsloc, 'WMC': wmc, 'CF*': cf, 'LCOM*': lcom, 'Label': label})
    correlation_matrix = df.corr(method='spearman')

    correlations_flat = correlation_matrix.stack().rename("correlation").reset_index()

    # Bokeh plot setup
    labels = list(correlation_matrix.columns)
    colors = RdBu[9][::-1]

    p = figure(width=600, height=600,
               title="Spearman's Correlation Heatmap for Metrics", x_range=labels, y_range=labels,
               toolbar_location=None, tools='', x_axis_location="above")

    p.rect(x="level_0", y="level_1", width=1, height=1,
           source=correlations_flat,
           fill_color=transform('correlation', LinearColorMapper(palette=colors, low=-1, high=1)),
           line_color=None)

    p.text(x="level_0", y="level_1", text="correlation",
           source=correlations_flat, text_align="center", text_baseline="middle")

    p.xaxis.major_label_orientation = np.pi / 4
    p.yaxis.major_label_orientation = np.pi / 4
    p.xaxis.axis_label = "Metrics"
    p.yaxis.axis_label = "Metrics"

    color_mapper = LinearColorMapper(palette=colors, low=-1, high=1)
    color_bar = ColorBar(color_mapper=color_mapper, location=(0, 0))
    p.add_layout(color_bar, 'right')

    show(column(p, sizing_mode='scale_width'))

complexity = [12, 1, 1, 1, 2, 1, 1, 5, 6, 3, 9, 1, 1, 20, 2]
wmc = [11, 1, 1, 1, 2, 1, 1, 5, 6, 3, 9, 1, 1, 20, 2]
lsloc = [18, 1, 4, 6, 7, 6, 3, 19, 6, 8, 13, 1, 1, 49, 6]
psloc = [43, 7, 11, 14, 17, 13, 9, 32, 24, 17, 41, 7, 6, 111, 19]
cf = [0, 1, 2, 4, 4, 2, 1, 5, 3, 4, 4, 2, 1, 22, 4]
lcom = [1, 0, 0, 0, 0, 0, 0, 0, 0.8, 0, 0.84, 0, 0, 0.82, 0]
label = ["CustomMath", "ComputeEmpty", "ComputeMaximum", "ComputeMean", "ComputeMeanAbsoluteDeviation", "ComputeMedian", "ComputeMinimum", "ComputeMode", "ComputeObserver", "ComputeStandardDeviation", "Event", "StringCheckingUtils", "App", "Controller", "MetricsticsUI"]

# To get scatter plot
scatter_plot(lsloc, wmc, label, "LSLOC", "WMC")

# checking normality for metrics
is_psloc_normal, is_lsloc_normal, is_wmc_normal, is_cf_normal, is_lcom_normal = check_normality(lsloc, wmc, psloc, cf, lcom)
print("psloc is normal:", is_psloc_normal)
print("lsloc is normal:", is_lsloc_normal)
print("wmc is normal:", is_wmc_normal)
print("cf is normal:", is_cf_normal)
print("lcom is normal:", is_lcom_normal)

# getting spearman's coefficient for wmc and lsloc
spearman_correlation_coefficient = rank_correlation_coefficient(wmc, lsloc)
print(f"Spearman correlation coefficient: {spearman_correlation_coefficient}")

# To generate heatmap    
generate_heatmap(psloc, lsloc, wmc, cf, lcom)