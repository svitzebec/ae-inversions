#!/usr/bin/python3

import pandas as pd
import matplotlib.pyplot as plt

# TODO: change the name of the csv file to the actual file results are stored in
dataFrame = pd.read_csv("InversionTestResults.csv", sep=",",names=['algorithm', 'n', 'sequence', 'repetitions', 'result', 'time'], index_col=[2,0,1])
dataFrame.sort_index(inplace=True)
dataFrame['timeNormal'] = dataFrame['time'] / dataFrame['repetitions']

algSlow = ['bf', 'is1', 'is2']
algFast = ['ms1', 'ms2', 'ms3']
algAll = algSlow + algFast
algAllAndHybrid = algAll + ['hybrid']

seqAll = ['INC', 'DEC', 'RND']

tinyX = list(range(10, 151, 5))
smallX = list(range(10000, 50001, 5000))
largeX = list(range(100000, 1000001, 100000))

def drawPlotMultipleAlgorithms(xNumbers, name, sequence, algorithms):
	currentDataFrame = dataFrame.loc[sequence, algorithms, xNumbers]

	# prepare figure
	figure = plt.figure()
	figure.suptitle(name, fontsize=16)

	minX = min(xNumbers)
	maxX = max(xNumbers)
	xPadding = (maxX - minX) * 0.1
	plt.xlim(minX - xPadding, maxX + xPadding)

	minY = min(currentDataFrame['timeNormal'])
	maxY = max(currentDataFrame['timeNormal'])
	yPadding = (maxY - minY) * 0.1
	plt.ylim(minY - yPadding, maxY + yPadding)

	plt.xlabel('n')
	plt.ylabel('time in seconds')

	for algorithm in algorithms:
		yNumbers = currentDataFrame.loc[sequence, algorithm]['timeNormal']
		plt.plot(xNumbers, yNumbers, '-o', label=algorithm)
	plt.legend(loc='best')

	figure.savefig(name+'.pdf', dpi=96)
	plt.close('all')

def drawPlotSingleAlgorithm(xNumbers, name, sequences, algorithm):
	currentDataFrame = dataFrame.loc[sequences, algorithm, xNumbers]

	# prepare figure
	figure = plt.figure()
	figure.suptitle(name, fontsize=16)

	minX = min(xNumbers)
	maxX = max(xNumbers)
	xPadding = (maxX - minX) * 0.1
	plt.xlim(minX - xPadding, maxX + xPadding)

	minY = min(currentDataFrame['timeNormal'])
	maxY = max(currentDataFrame['timeNormal'])
	yPadding = (maxY - minY) * 0.1
	plt.ylim(minY - yPadding, maxY + yPadding)

	plt.xlabel('n')
	plt.ylabel('time in seconds')

	for sequence in sequences:
		yNumbers = currentDataFrame.loc[sequence, algorithm]['timeNormal']
		plt.plot(xNumbers, yNumbers, '-o', label=sequence)
	plt.legend(loc='best')

	figure.savefig(name+'.pdf', dpi=96)
	plt.close('all')

drawPlotMultipleAlgorithms(tinyX, 'tiny-inc', 'INC', algAllAndHybrid)
drawPlotMultipleAlgorithms(tinyX, 'tiny-dec', 'DEC', algAllAndHybrid)
drawPlotMultipleAlgorithms(tinyX, 'tiny-rnd', 'RND', algAllAndHybrid)

drawPlotMultipleAlgorithms(smallX, 'small-inc', 'INC', algAll)
drawPlotMultipleAlgorithms(smallX, 'small-dec', 'DEC', algAll)
drawPlotMultipleAlgorithms(smallX, 'small-rnd', 'RND', algAll)

drawPlotMultipleAlgorithms(largeX, 'large-inc', 'INC', algFast)
drawPlotMultipleAlgorithms(largeX, 'large-dec', 'DEC', algFast)
drawPlotMultipleAlgorithms(largeX, 'large-rnd', 'RND', algFast)

drawPlotSingleAlgorithm(smallX, 'small-bf', seqAll, 'bf')
drawPlotSingleAlgorithm(smallX, 'small-is1', seqAll, 'is1')
drawPlotSingleAlgorithm(smallX, 'small-is2', seqAll, 'is2')
drawPlotSingleAlgorithm(smallX, 'small-ms1', seqAll, 'ms1')
drawPlotSingleAlgorithm(smallX, 'small-ms2', seqAll, 'ms2')
drawPlotSingleAlgorithm(smallX, 'small-ms3', seqAll, 'ms3')

