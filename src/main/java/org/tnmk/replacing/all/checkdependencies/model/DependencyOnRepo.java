package org.tnmk.replacing.all.checkdependencies.model;

public class DependencyOnRepo {
  private Dependency dependency;
  private String link;

  public DependencyOnRepo(Dependency dependency, String link) {
    this.dependency = dependency;
    this.link = link;
  }

  @Override public String toString() {
    return "DependencyOnRepo{" +
        "dependency=" + dependency +
        ", link='" + link + '\'' +
        '}';
  }

  public Dependency getDependency() {
    return dependency;
  }

  public void setDependency(Dependency dependency) {
    this.dependency = dependency;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
