/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package org.jgap.gp.function;

import org.jgap.*;
import org.jgap.gp.*;
import org.jgap.gp.impl.*;
import org.jgap.util.*;

/**
 * The power operation.
 *
 * @author Klaus Meffert
 * @since 3.0
 */
public class Pow
    extends MathCommand implements ICloneable {
  public Pow(final GPConfiguration a_conf, Class a_returnType)
      throws InvalidConfigurationException {
    super(a_conf, 2, a_returnType);
  }

  public String toString() {
    return "&1 ^ &2";
  }

  /**
   * @return textual name of this command
   *
   * @author Klaus Meffert
   * @since 3.2
   */
  public String getName() {
    return "Power";
  }

  public int execute_int(ProgramChromosome c, int n, Object[] args) {
    int i = c.execute_int(n, 0, args);
    int j = c.execute_int(n, 1, args);
    // clip to -10000 -> 20
    return (int) Math.pow(Math.max( -10000.0f, Math.min(i, 20.0f)),
                          Math.max( -10000.0f, Math.min(j, 20.0f)));
  }

  public float execute_float(ProgramChromosome c, int n, Object[] args) {
    float f = c.execute_float(n, 0, args);
    float g = c.execute_float(n, 1, args);
    // clip to -10000 -> 20
    return (float) Math.pow(Math.max( -10000.0f, Math.min(f, 20.0f)),
                            Math.max( -10000.0f, Math.min(g, 20.0f)));
  }

  public double execute_double(ProgramChromosome c, int n, Object[] args) {
    double f = c.execute_double(n, 0, args);
    double g = c.execute_double(n, 1, args);
    // clip to -10000 -> 20
    return Math.pow(Math.max( -10000.0, Math.min(f, 20.0)),
                    Math.max( -10000.0, Math.min(g, 20.0)));
  }

  public Object execute_object(ProgramChromosome c, int n, Object[] args) {
    return ( (Compatible) c.execute_object(n, 0, args)).execute_pow(
        c.execute_object(n, 1, args));
  }

  protected interface Compatible {
    public Object execute_pow(Object o);
  }
  /**
   * Clones the object. Simple and straight forward implementation here.
   *
   * @return cloned instance of this object
   *
   * @author Klaus Meffert
   * @since 3.4
   */
  public Object clone() {
    try {
      Pow result = new Pow(getGPConfiguration(), getReturnType());
      return result;
    } catch (Exception ex) {
      throw new CloneException(ex);
    }
  }
}