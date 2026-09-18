import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

try:
    df = pd.read_csv('results.csv')
except FileNotFoundError:
    print("Ошибка: Файл results.csv не найден в текущей папке.")
    exit()


df['ratio'] = df['comparisons'] / (df['n'] * np.log2(df['n']))

def create_plot(metric_col, title, ylabel, filename):
    plt.figure(figsize=(10, 6))

    algorithms = df['algorithm'].unique()
    inputs = df['input'].unique()

    markers = ['o', 's', '^']
    colors = {'MergeSort': 'blue', 'QuickSort': 'red'}

    for algo in algorithms:
        for i, inp_type in enumerate(inputs):
            subset = df[(df['algorithm'] == algo) & (df['input'] == inp_type)].sort_values(by='n')

            # Строим линию
            plt.plot(
                subset['n'],
                subset[metric_col],
                marker=markers[i % len(markers)],
                color=colors.get(algo, 'black'),
                linestyle='-' if inp_type == 'random' else '--' if inp_type == 'sorted' else ':',
                label=f"{algo} ({inp_type})"
            )


    plt.xscale('log')
    plt.title(title, fontsize=14, fontweight='bold')
    plt.xlabel('Array Size (n)', fontsize=12)
    plt.ylabel(ylabel, fontsize=12)

    plt.grid(True, which="both", linestyle='--', alpha=0.7)

    plt.legend(title='Algorithm & Input', bbox_to_anchor=(1.05, 1), loc='upper left')


    plt.tight_layout()
    plt.savefig(filename, dpi=300)
    print(f"График сохранен: {filename}")
    plt.close()


create_plot(
    metric_col='time_ms',
    title='Time vs Array Size (n)',
    ylabel='Time (ms)',
    filename='1_time_vs_n.png'
)

create_plot(
    metric_col='max_depth',
    title='Max Recursion Depth vs Array Size (n)',
    ylabel='Max Depth',
    filename='2_depth_vs_n.png'
)

create_plot(
    metric_col='ratio',
    title='Ratio [Comparisons / (n * log2(n))] vs Array Size (n)',
    ylabel='Ratio',
    filename='3_ratio_vs_n.png'
)
