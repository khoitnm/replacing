package org.tnmk.replacing.all.checkdependencies;

import java.util.Objects;

public class CheckedDependency {
  private final Dependency dependency;
  private final boolean accessible;

  public CheckedDependency(Dependency dependency, boolean accessible) {
    this.dependency = dependency;
    this.accessible = accessible;
  }

  @Override public String toString() {
    return "CheckedDependency{" +
        "dependency=" + dependency +
        ", accessible=" + accessible +
        '}';
  }

  public Dependency getDependency() {
    return dependency;
  }

  public boolean isAccessible() {
    return accessible;
  }
}
