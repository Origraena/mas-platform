/*
 Copyright (C) 2012 William James Dyce

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wjd.gui;

/** 
* @author wdyce
* @seen 01-Oct-2012
*/
public interface IDynamic 
{  
  /**
   * Update the object a variable amount based on the specified delta-time.
   * 
   * @param t_delta the number of milliseconds that have elapsed.
   */
  public void update(int t_delta);
  
  
}