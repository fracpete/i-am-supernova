/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * StatisticsTable.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.supernova.gui;

import nz.ac.waikato.cms.gui.core.JTableHelper;
import nz.ac.waikato.cms.supernova.io.AbstractOutputGenerator;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.util.List;
import java.util.Map;

/**
 * Editable table for statistics/color.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class StatisticsTable
  extends JTable {

  public static class Model
    extends AbstractTableModel {

    /** the statistics. */
    protected Map<String,List<Double>> m_Statistics;

    /** the colors. */
    protected Map<String,Color> m_Colors;

    /**
     * Initializes the model.
     */
    public Model() {
      super();
      m_Statistics = AbstractOutputGenerator.getDefaultStatistics();
      m_Colors     = AbstractOutputGenerator.getDefaultColors();
    }

    /**
     * Returns the number of rows.
     *
     * @return		the nummber of rows
     */
    @Override
    public int getRowCount() {
      return AbstractOutputGenerator.MEASURES.length;
    }

    /**
     * Returns the number of columns.
     *
     * @return		the number of columns
     */
    @Override
    public int getColumnCount() {
      return 4;
    }

    /**
     * Returns the column name.
     *
     * @param column	the column
     * @return		the name
     */
    @Override
    public String getColumnName(int column) {
      switch (column) {
	case 0:
	  return "Measure";
	case 1:
	  return "Score";
	case 2:
	  return "Percentile";
	case 3:
	  return "Color";
	default:
	  throw new IllegalArgumentException("Invalid column index: " + column);
      }
    }

    /**
     * Returns the class of the column.
     *
     * @param columnIndex	the column
     * @return			the class
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
      switch (columnIndex) {
	case 0:
	  return String.class;
	case 1:
	case 2:
	  return Double.class;
	case 3:
	  return Color.class;
	default:
	  throw new IllegalArgumentException("Invalid column index: " + columnIndex);
      }
    }

    /**
     * Returns whether a cell is editable.
     *
     * @param rowIndex		the row
     * @param columnIndex	the column
     * @return			true if editable
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return (columnIndex > 0);
    }

    /**
     * Returns the value at the given position.
     *
     * @param rowIndex		the row
     * @param columnIndex	the column
     * @return			the cell value
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      switch (columnIndex) {
	case 0:
	  return AbstractOutputGenerator.MEASURES[rowIndex];
	case 1:
	  return m_Statistics.get(AbstractOutputGenerator.MEASURES[rowIndex]).get(0);
	case 2:
	  return m_Statistics.get(AbstractOutputGenerator.MEASURES[rowIndex]).get(1);
	case 3:
	  return m_Colors.get(AbstractOutputGenerator.MEASURES[rowIndex]);
	default:
	  throw new IllegalArgumentException("Invalid column index: " + columnIndex);
      }
    }

    /**
     * Sets the value at the specified position.
     *
     * @param aValue		the new value
     * @param rowIndex		the row
     * @param columnIndex	the column
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      switch (columnIndex) {
	case 1:
	  m_Statistics.get(AbstractOutputGenerator.MEASURES[rowIndex]).set(0, (Double) aValue);
	  break;
	case 2:
	  m_Statistics.get(AbstractOutputGenerator.MEASURES[rowIndex]).set(1, (Double) aValue);
	  break;
	case 3:
	  m_Colors.put(AbstractOutputGenerator.MEASURES[rowIndex], (Color) aValue);
      }
    }

    /**
     * Returns the statistics.
     *
     * @return		the statistics
     */
    public Map<String,List<Double>> getStatistics() {
      return m_Statistics;
    }

    /**
     * Returns the colors.
     *
     * @return		the colors
     */
    public Map<String,Color> getColors() {
      return m_Colors;
    }
  }

  /**
   * Initializes the table.
   */
  public StatisticsTable() {
    super(new Model());
    setAutoResizeMode(StatisticsTable.AUTO_RESIZE_OFF);
    setDefaultRenderer(Color.class, new ColorRenderer());
    setDefaultEditor(Color.class, new ColorEditor());
    setPreferredScrollableViewportSize(getPreferredSize());
    JTableHelper.setOptimalColumnWidth(this);
  }

  /**
   * Returns the statistics.
   *
   * @return		the statistics
   */
  public Map<String,List<Double>> getStatistics() {
    return ((Model) getModel()).getStatistics();
  }

  /**
   * Returns the colors.
   *
   * @return		the colors
   */
  public Map<String,Color> getColors() {
    return ((Model) getModel()).getColors();
  }
}
