package org.tnmk.replacing.all.checkdependencies.model;

public class AccessibleDependency {
  private final DependencyOnRepo dependencyOnRepo;
  private final boolean accessible;

  public AccessibleDependency(DependencyOnRepo dependencyOnRepo, boolean accessible) {
    this.dependencyOnRepo = dependencyOnRepo;
    this.accessible = accessible;
  }

  @Override public String toString() {
    return "CheckedDependency{" +
        "dependency=" + dependencyOnRepo +
        ", accessible=" + accessible +
        '}';
  }

  public DependencyOnRepo getDependencyOnRepo() {
    return dependencyOnRepo;
  }

  public boolean isAccessible() {
    return accessible;
  }
}
