import matplotlib.pyplot as plt
import statistics
from bokeh.plotting import figure, show
from bokeh.models import Span, Legend, LegendItem
from bokeh.io import output_notebook
import numpy as np
    
def generate_curve_with_stats(values, labels, title):

    sorted_values, sorted_labels = zip(*sorted(zip(values, labels)))

    y = list(range(1, len(sorted_values) + 1))
    plt.plot(sorted_values, y)
    plt.xlabel("Data Points")  # Switched x and y axis labels
    plt.ylabel("Values")       # Switched x and y axis labels
    plt.title(f"{title} Chart", fontsize=16, fontweight='bold')

    mean = statistics.mean(sorted_values)
    median = statistics.median(sorted_values)
    try:
        mode = statistics.mode(sorted_values)
    except statistics.StatisticsError:
        mode = "Multiple Modes"

    plt.axvline(x=mean, color='r', linestyle='--', label=f"Mean: {mean}")
    plt.axvline(x=median, color='g', linestyle='--', label=f"Median: {median}")
    plt.axvline(x=mode, color='b', linestyle='--', label=f"Mode: {mode}")

    plt.yticks(y, sorted_labels)
    plt.legend()
    plt.show()

def bar_chart(wmc_data, cf_data, lcom_data, function_labels):
    mean_wmc = np.mean(wmc_data)
    mean_cf = np.mean(cf_data)
    mean_lcom = np.mean(lcom_data)

    num_functions = len(function_labels)
    bar_width = 0.25
    index = np.arange(num_functions)

    plt.figure(figsize=(16, 8))

    plt.barh(index - bar_width, wmc_data, bar_width, label='WMC', color='mediumpurple')
    plt.barh(index, cf_data, bar_width, label='CF*', color='maroon')
    plt.barh(index + bar_width, lcom_data, bar_width, label='LCOM*', color='teal')


    plt.axvline(mean_wmc, linestyle='dashed', color='mediumpurple', label=f'Mean of WMC: {mean_wmc:.2f}')
    plt.axvline(mean_cf, linestyle='dashed', color='maroon', label=f'Mean of CF*: {mean_cf:.2f}')
    plt.axvline(mean_lcom, linestyle='dashed', color='teal', label=f'Mean of LCOM: {mean_lcom:.2f}')

    plt.xlabel('Values',fontsize=18)
    plt.ylabel('Classes',fontsize=18)
    plt.title('Bar Chart for WMC, CF*, LCOM* and averages',fontsize=22)

    plt.xlim(0, max(max(wmc_data), max(cf_data), max(lcom_data)) + 1)
    plt.yticks(index, function_labels, fontsize=18)
    plt.legend(fontsize=18)
    plt.tight_layout()
    plt.show()

def bokeh_bar_chart(wmc_data, cf_data, lcom_data, function_labels):
    mean_wmc = np.mean(wmc_data)
    mean_cf = np.mean(cf_data)
    mean_lcom = np.mean(lcom_data)

    num_functions = len(function_labels)
    bar_width = 0.25
    index = np.arange(num_functions)

    output_notebook()

    p = figure(y_range=function_labels, height=800, width=400, title="Bar Chart for WMC, CF*, LCOM* and averages")
    p.hbar(y=index - bar_width, right=wmc_data, height=bar_width, color="maroon", legend_label='WMC')
    p.hbar(y=index, right=cf_data, height=bar_width, color="blue", legend_label='CF*')
    p.hbar(y=index + bar_width, right=lcom_data, height=bar_width, color="green", legend_label='LCOM*')

    p.circle(0, 0, color="maroon", legend_label='Mean of WMC', alpha=0)
    p.circle(0, 0, color="blue", legend_label='Mean of CF*', alpha=0)
    p.circle(0, 0, color="green", legend_label='Mean of LCOM*', alpha=0)

    p.add_layout(Span(location=mean_wmc, dimension='height', line_color='maroon', line_dash='dashed', line_width=2))
    p.add_layout(Span(location=mean_cf, dimension='height', line_color='blue', line_dash='dashed', line_width=2))
    p.add_layout(Span(location=mean_lcom, dimension='height', line_color='green', line_dash='dashed', line_width=2))

    p.y_range.range_padding = 0.1
    p.ygrid.grid_line_color = None
    p.axis.minor_tick_line_color = None
    p.outline_line_color = None

    legend_items = [
        LegendItem(label='Mean of WMC', renderers=[p.renderers[3]]),
        LegendItem(label='Mean of CF*', renderers=[p.renderers[4]]),
        LegendItem(label='Mean of LCOM*', renderers=[p.renderers[5]])
    ]

    legend = Legend(items=legend_items, location='center')
    p.add_layout(legend, 'right')

    show(p)

wmc = [11, 1, 1, 1, 2, 1, 1, 5, 6, 3, 9, 1, 1, 20, 2]
cf = [0, 1, 2, 4, 4, 2, 1, 5, 3, 4, 4, 2, 1, 22, 4]
lcom = [1, 0, 0, 0, 0, 0, 0, 0, 0.8, 0, 0.84, 0, 0, 0.82, 0]
label = ["CustomMath", "ComputeEmpty", "ComputeMaximum", "ComputeMean", "ComputeMeanAbsoluteDeviation", "ComputeMedian", "ComputeMinimum", "ComputeMode", "ComputeObserver", "ComputeStandardDeviation", "Event", "StringCheckingUtils", "App", "Controller", "MetricsticsUI"]

# generate_curve_with_stats(wmc, label, "WMC")
# generate_curve_with_stats(cf, label, "IsClient Relationship")
# generate_curve_with_stats(lcom, label, "LCOM*")
bar_chart(wmc, cf, lcom, label)
# bokeh_bar_chart(wmc, cf, lcom, label)