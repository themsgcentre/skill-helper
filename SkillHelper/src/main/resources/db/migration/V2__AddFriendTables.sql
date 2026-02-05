CREATE TABLE [dbo].[Friend] (
    [User] [nvarchar](16),
    [Friend] [nvarchar](16),
    CONSTRAINT [FRIEND_PK_FRIEND] PRIMARY KEY([User], [Friend]),
    CONSTRAINT [FRIEND_FK_USER] FOREIGN KEY ([User]) REFERENCES [User]([Username]),
    CONSTRAINT [FRIEND_FK_FRIEND] FOREIGN KEY ([Friend]) REFERENCES [User]([Username]),
    CONSTRAINT [FRIEND_DIFFER] CHECK ([User] <> [Friend])
);

CREATE TABLE [dbo].[Request] (
    [User] [nvarchar](16),
    [Request] [nvarchar](16),
    CONSTRAINT [REQUEST_PK_REQUEST] PRIMARY KEY([User], [Request]),
    CONSTRAINT [REQUEST_FK_USER] FOREIGN KEY ([User]) REFERENCES [User]([Username]),
    CONSTRAINT [REQUEST_FK_REQUEST] FOREIGN KEY ([Request]) REFERENCES [User]([Username]),
    CONSTRAINT [REQUEST_DIFFER] CHECK ([User] <> [Request])
);